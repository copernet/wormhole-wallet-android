package core.core;

import android.os.Handler;
import android.os.Looper;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import core.java.util.HashMap;
import core.java.util.HashSet;
import core.java.util.Iterator;
import core.java.util.List;
import core.java.util.Map;
import core.java.util.Set;
import core.core.Deserialize.Holder;
import core.util.Server;
import whc.com.whc_wallet.util.Utils;

public class Network extends Thread {
    private static Network sNetwork;

    private Map scripthashMap;
    private HashSet hashes;
    private Map blockchains;
    private boolean downloadingHeaders = false;
    private volatile int message_id;
    private Map sub_cache = new HashMap();
    private Map unanswered_requests = new HashMap();
    private Set subscribed_addresses = new HashSet();
    private Integer blockchain_index;
    private Map subscriptions = new HashMap();
    private ApplicationContext context;
    private ConnectionThread connectionThread;
    private NoSecureStorage storage;
    private Config config;
    private String responseString = "";

    public Network(ApplicationContext context, NoSecureStorage storage) {
        super("NetworkThread");
        this.context = context;
        this.storage = storage;
        this.config = context.getConfig();
        scripthashMap = new HashMap();
        hashes = new HashSet();
        blockchains = BlockchainsUtil.read_blockchain();
        blockchain_index = (Integer) config.get("blockchain_index", new Integer(BlockchainsUtil.CHECKPOINT));
        if (!blockchains.keySet().contains(blockchain_index)) {
            blockchain_index = new Integer(BlockchainsUtil.CHECKPOINT);
        }
        connectionThread = new ConnectionThread(context.getTcpConnection());
        connectionThread.setTip(0);
        connectionThread.setBlockchain(null);
        connectionThread.setMode("default");
        connectionThread.setTip_header(null);
        connectionThread.setRequest(0);

        getBalance();
        queue_request("blockchain.headers.subscribe", new String[]{});
        queue_request("server.version", new String[]{"3.1.5", "1.1"});
        sNetwork = this;
    }

    public static Network getInstance() {
        return sNetwork;
    }

    public void initHeaderFile() {
        try {
            NetworkUtil.init_header_file(this, (Blockchain) (blockchains.get(new Integer(BlockchainsUtil.CHECKPOINT))));
            while (isDownloadingHeaders()) {
                Thread.sleep(1000);
            }
            blockchain().update_size();
        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {

        }
    }

    private Timer timer = null;
    private TimerTask balanceTask = new TimerTask() {
        @Override
        public void run() {
            getBalance();
            handler.postDelayed(this, 30 * 1000);
        }
    };
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            try {
                get_history();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            get_transaction();
//            if (context.isLatestBlock()) {
                try {
//					get_history();
                    varify(getTxHashHeight());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//            } else {
//                LogUtils.e("not latest block!");
//            }
            handler.postDelayed(this, 5 * 60 * 1000);
        }
    };
    Handler handler = new Handler(Looper.getMainLooper());

    public void run() {

        while (true) {
            try {
                waitOnSockets();
                //maintain_requests();
                process_pending_sends();
                if (null == timer) {
                    timer = new Timer();
                    handler.postDelayed(task, 5000);
                    handler.postDelayed(balanceTask, 5000);
//					timer.schedule(task, 0, 50000);
                    subscribeAddresses();
                }


            } catch (Exception e) {
                LogUtils.e("qtest", e.toString());
                e.printStackTrace();
            }
        }
    }

    private void waitOnSockets() throws JSONException, ClassCastException, IllegalArgumentException, NullPointerException, RuntimeException, IOException {
        if (connectionThread.num_requests() > 0) {
            connectionThread.sendRequest();
        }
        processResponses();

    }

    private void processResponses() throws JSONException, ClassCastException, IllegalArgumentException, NullPointerException, RuntimeException, IOException {
        List responses = connectionThread.getResponses();

        Iterator it = responses.iterator();
        while (it.hasNext()) {
            Set callbacks = new HashSet();
            RequetResponseTuple key = (RequetResponseTuple) it.next();
            it.remove();
            RequestQueueItem request = key.getRequest();
            JSONObject response = key.getResponse();
            String k = "";
            if (request != null) {
                unanswered_requests.remove(request.getId());
                response.put("method", request.getMethod());
                JSONArray array = new JSONArray();
                Object[] o = request.getParams();
                for (int i = 0; i < o.length; i++) {
                    array.put(o[i]);
                }
                response.put("params", array);
                if ("blockchain.scripthash.subscribe".equals(request.getMethod())) {
                    subscribed_addresses.add(request.getParams()[0]);
                    get_history(response);
                }
                if ("blockchain.scripthash.get_history".equals(request.getMethod())) {
                    get_tx(response);
                }
                if ("blockchain.scripthash.get_balance".equals(request.getMethod())) {
                    onGetBalance(response);
                }
                if ("blockchain.transaction.get".equals(request.getMethod())) {
                    get_raw_tx(response);
                }
                if ("blockchain.transaction.get_merkle".equals(request.getMethod())) {
                    get_merkle(response);
                }
                if ("blockchain.transaction.broadcast".equals(request.getMethod())) {
                    responseString = response.toString();
                }
            } else {
                String method = response.optString("method");
                JSONArray params = response.optJSONArray("params");
                if (params == null)
                    k = method;
                else
                    k = method + ":" + params.get(0);
                if ("blockchain.headers.subscribe".equals(method)) {
                    response.put("result", params.get(0));
                    response.put("params", new JSONArray());
                } else if ("blockchain.scripthash.subscribe".equals(method)) {
                    response.put("result", params.get(1));
                    response.put("params", new JSONArray().put(params.get(0)));
                    String address = AddressUtil.getLegacyAddressByScripthash(params.getString(0));
                    scripthashMap.put(response.getString("result"), address);
                    queue_request("blockchain.scripthash.get_history", new String[]{params.getString(0)});

                }
                callbacks = (Set) subscriptions.get(k);
            }
            if (request != null && request.getMethod() != null && request.getMethod().endsWith(".subscribe")) {
                sub_cache.put(k, response);
            }
            process_response(response, callbacks);
            connectionThread.getResponses().remove(key);
        }
    }

    private void on_get_fee(JSONObject response) throws JSONException {
        JSONObject fee = storage.get("relayfee", new JSONObject());
        fee.put("fee", response.getDouble("result"));
        storage.put("relayfee", fee);
    }

    public static BigDecimal sConfirmedMoney = new BigDecimal(0);
    public static BigDecimal sUnConfirmedMoney = new BigDecimal(0);
    private void onGetBalance(JSONObject response) throws JSONException {
        JSONObject jRes = response.getJSONObject("result");
        String conMoney = jRes.optString("confirmed", "0");
        sConfirmedMoney = new BigDecimal(conMoney);
        String unconMoney = jRes.optString("unconfirmed", "0");
        sUnConfirmedMoney = new BigDecimal(unconMoney);
    }

    private void get_merkle(JSONObject response) throws JSONException {
        Verifier verifer = new Verifier();
        VerifiedTx verified = verifer.verify_merkle(response, this);
        if (null == verified) return;
        JSONObject verifiedTxs = storage.get("verified_tx3", new JSONObject());
        JSONArray array = new JSONArray();
        array.put(verified.getHeight());
        array.put(verified.getTimestamp());
        array.put(verified.getPos());
        verifiedTxs.put(verified.getHash(), array);
        storage.put("verified_tx3", verifiedTxs);
        storage.write();

    }

    public boolean isMine(String address) {
        if (null == address) return false;
        JSONObject addresses = storage.get("addr_history", new JSONObject());
        return addresses.has(address);
    }

    private void get_raw_tx(JSONObject response) throws JSONException {
        JSONObject transactions = storage.get("transactions", new JSONObject());
        String transactionHash = response.getJSONArray("params").getString(0);
        if (response.has("error")) return;
        transactions.put(transactionHash, response.getString("result"));
        storage.put("transactions", transactions);

        Deserialize d = Deserialize.parse(response.getString("result"));

        JSONObject txo = storage.get("txo", new JSONObject());

        JSONObject txi = storage.get("txi", new JSONObject());
        JSONObject txiHash = txi.getJSONObject(transactionHash);
        Vector inputs = d.getInputs();
        JSONArray inArray = new JSONArray();
        for (int i = 0; i < inputs.size(); i++) {
            RawInput input = (RawInput) inputs.elementAt(i);
            Vector v = d.script_GetOp(input.getScript());
            int[] match = new int[]{AddressUtil.OP_PUSHDATA4, AddressUtil.OP_PUSHDATA4};
            String address = "";
            if (d.match_decoded(v, match)) {
                String xpub = BlockchainsUtil.bh2u(((Holder) v.elementAt(1)).getVch());
                String ripeHash = Bitcoin.ripeHash(xpub);
                address = AddressUtil.legacyAddrfromPubKeyHash(ripeHash);
            }
            if (isMine(address)) {
                JSONArray array = new JSONArray();
                array.put(input.getTxHash() + ":" + input.getTxIndex());

                if (txo.has(input.getTxHash())) {
                    JSONObject txoKey = txo.getJSONObject(input.getTxHash());
                    if (txoKey.length() > 0) {
                        JSONArray txoArr = txoKey.getJSONArray(address);
                        for (int txoI = 0; txoI < txoArr.length(); txoI++) {
                            JSONArray nestTxoArray = txoArr.getJSONArray(txoI);

                            if (nestTxoArray.getInt(0) == input.getTxIndex()) {
                                array.put(nestTxoArray.getInt(1));
                            }
                        }
                    }
                }
                inArray.put(array);
                txiHash.put(address, inArray);
            }
        }
        txi.put(transactionHash, txiHash);
        storage.put("txi", txi);

        JSONObject txoHash = txo.getJSONObject(transactionHash);
        JSONArray outArray = new JSONArray();

        Vector outputs = d.getOutputs();
        for (int i = 0; i < d.getOutputCount(); i++) {
            RawOutput output = (RawOutput) outputs.elementAt(i);
            JSONArray array = new JSONArray();
            array.put(i);
            array.put(output.getAmount());

            try {
                String address = AddressUtil.getLegacyAddressByScripthash(output.getScript());
                if (isMine(address)) {
                    outArray.put(array);
                    txoHash.put(address, outArray);
                }
            } catch (Exception e) {
                LogUtils.d(outputs);
            }
        }

        txo.put(transactionHash, txoHash);
        storage.put("txo", txo);

        storage.write();
    }

    private void get_tx(JSONObject response) throws JSONException {
        String scripthashAddr = response.getJSONArray("params").getString(0);
        String legacyAddress = (String) scripthashMap.get(scripthashAddr);
        JSONObject history = storage.get("addr_history", new JSONObject());
        JSONArray itemHistory = history.getJSONArray(legacyAddress);
        JSONObject txo = storage.get("txo", new JSONObject());
        JSONObject txi = storage.get("txi", new JSONObject());
        JSONObject transactions = storage.get("transactions", new JSONObject());

        JSONArray result = response.getJSONArray("result");
        for (int i = 0; i < result.length(); i++) {
            JSONObject jsonObject = result.getJSONObject(i);
            JSONArray array = new JSONArray();
            String hash = jsonObject.getString("tx_hash");
            array.put(hash);
            array.put(jsonObject.getInt("height"));
            if (!contain(itemHistory, array)) {
                itemHistory.put(array);
            }
            hashes.add(hash);
            if (!txo.has(hash)) {
                txo.put(hash, new JSONObject());
            }
            if (!txi.has(hash)) {
                txi.put(hash, new JSONObject());
            }
            if (!transactions.has(hash)) {
                transactions.put(hash, "");
            }
            if ("".equals(transactions.get(hash))) {
                queue_request("blockchain.transaction.get", new String[]{hash});
            }
        }
        storage.put("txi", txi);
        storage.put("txo", txo);
        storage.put("transactions", transactions);
        storage.put("addr_history", history);

        storage.write();
    }


    private boolean contain(JSONArray array, JSONArray item) throws JSONException {
        if (array.length() == 0) {
            return false;
        } else {
            for (int i = 0; i < array.length(); i++) {
                JSONArray getItem = array.getJSONArray(i);
                if (item.toString().equals(getItem.toString())) {
                    return true;
                }
            }
            return false;
        }
    }

    private void process_response(JSONObject response, Set callbacks) throws JSONException, ClassCastException, IllegalArgumentException, NullPointerException, RuntimeException, IOException {
        String error = response.optString("error");
        String method = response.optString("method");
        if ("server.version".equals(method)) {
            JSONArray result = response.optJSONArray("result");
            if (result != null) {
                LogUtils.d("Server Version " + result.getString(1));
            }
        } else if ("blockchain.headers.subscribe".equals(method)) {
            if (error != null) {
                JSONObject result = response.optJSONObject("result");
                on_notify_header(result);
            }
        } else if ("server.peers.subscribe".equals(method)) {
            if (error != null) {
                JSONArray result = response.optJSONArray("result");
                if (result != null) {
                    NetworkUtil.parse_servers(result);
                }
                notify("servers");
            }
        } else if ("blockchain.block.get_chunk".equals(method)) {
            on_get_chunk(response);
        } else if ("blockchain.block.get_header".equals(method)) {
            on_get_header(response);
        }

        if ("blockchain.relayfee".equals(method)) {
            on_get_fee(response);
        }

        if ("blockchain.relayfee".equals(method)) {
            on_get_fee(response);
        }
        /*Iterator it = callbacks.iterator();
        while(it.hasNext()) {
			Callback call = (Callback)it.next();
			call.call(response);
		}*/
    }

    public void on_get_chunk(JSONObject response) throws JSONException {
        //Handle receiving a chunk of block headers
        String error = response.optString("error");
        String result = response.optString("result");
        JSONArray params = response.optJSONArray("params");

        if (result == null || params == null || error.length() > 0) {
            LogUtils.d("bad response");
            return;
        }
        int index = params.getInt(0);
        /*if(connectionManager.getRequest() != index) {
			return;
		}*/
        LogUtils.e("qtest begin connect_chunk");
        boolean connect = connectionThread.getBlockchain().connect_chunk(index, result);
        LogUtils.e("qtest after connect_chunk");

        if (!connect) {
            connection_down(connectionThread.getServer());
            return;
        }
        LogUtils.e("chunk connected!");
        if (connectionThread.getBlockchain().height() < connectionThread.getTip()) {
            request_chunk(index + 1);
        } else {
            connectionThread.setRequest(0);
            connectionThread.setMode("default");

            LogUtils.d("catch up done" + connectionThread.getBlockchain().height());
            connectionThread.getBlockchain().set_catch_up(null);
            context.setLatestBlock(true);
            context.setLatestBlockHeight(connectionThread.getBlockchain().height());

        }
        notify("updated");
    }

    public void on_get_header(JSONObject response) throws ClassCastException, IllegalArgumentException, NullPointerException, JSONException, RuntimeException, IOException {
        //Handle receiving a single block header'''
        LogUtils.d("On get header ");
        JSONObject header = response.optJSONObject("result");
        if (header == null) {
            connection_down(connectionThread.getServer());
            return;
        }
        int height = header.optInt("block_height");
        if (connectionThread.getRequest() != height) {
            LogUtils.d("unsolicited header " + connectionThread.getRequest() + " " + height);
            connection_down(connectionThread.getServer());
            return;
        }
        Blockchain chain = BlockchainsUtil.check_header(header);
        int next_height = 0;
        LogUtils.d("connecting with backward " + connectionThread.getMode());
        if ("backward".equals(connectionThread.getMode())) {
            if (chain != null) {
                LogUtils.d("binary search");
                connectionThread.setMode("binary");
                connectionThread.setBlockchain(chain);
                connectionThread.setGood(height);
                next_height = (connectionThread.getBad() + connectionThread.getGood()) / 2;

            } else {
                if (height == 0) {
                    connection_down(connectionThread.getServer());
                    next_height = 0;
                } else {
                    connectionThread.setBad(height);
                    connectionThread.setBad_header(header);
                    int delta = connectionThread.getTip() - height;
                    next_height = Math.max(0, connectionThread.getTip() - 2 * delta);
                }
            }
        } else if ("binary".equals(connectionThread.getMode())) {
            if (chain != null) {
                connectionThread.setGood(height);
                connectionThread.setBlockchain(chain);
            } else {
                connectionThread.setBad(height);
                connectionThread.setBad_header(header);
            }
            LogUtils.d("good and bad " + connectionThread.getBad() + " " + connectionThread.getGood());
            if (connectionThread.getBad() != connectionThread.getGood() + 1) {
                next_height = (connectionThread.getBad() + connectionThread.getGood()) / 2;
            } else if (!connectionThread.getBlockchain().can_connect(connectionThread.getBad_header(), false)) {
                connection_down(connectionThread.getServer());
                next_height = 0;
            } else {

                Blockchain branch = (Blockchain) blockchains.get(new Integer(connectionThread.getBad()));
                if (branch != null) {
                    if (branch.check_header(connectionThread.getBad_header())) {
                        LogUtils.d("joining chain" + connectionThread.getBad());
                        next_height = 0;
                    } else if (branch.parent().check_header(header)) {
                        connectionThread.setBlockchain(branch.parent());
                        next_height = 0;
                    } else {
                        LogUtils.d("checkpoint conflicts with existing fork" + branch.getPath());
                        // branch.write('', 0);
                        branch.save_header(connectionThread.getBad_header());
                        connectionThread.setMode("catch_up");
                        connectionThread.setBlockchain(branch);
                        next_height = connectionThread.getBad() + 1;
                        connectionThread.getBlockchain().set_catch_up(connectionThread.getServer());
                    }
                } else {
                    int bh = connectionThread.getBlockchain().height();
                    next_height = 0;
                    if (bh > connectionThread.getGood()) {
                        if (connectionThread.getBlockchain().check_header(connectionThread.getBad_header())) {
                            Blockchain b = connectionThread.getBlockchain().fork(connectionThread.getBad_header());
                            blockchains.put(new Integer(connectionThread.getBad()), b);
                            connectionThread.setBlockchain(b);
                            LogUtils.d("new chain " + b.get_checkpoint());
                            connectionThread.setMode("catch_up");
                            next_height = connectionThread.getBad() + 1;
                            connectionThread.getBlockchain().set_catch_up(connectionThread.getServer());
                        } else if (bh == connectionThread.getBad_header().getInt("block_height")) {
                            //TODO(qiaochuanbei) 这条分支是处理了一个块的回滚情况
                            connectionThread.getBlockchain().save_header(connectionThread.getBad_header());
                            connectionThread.setMode("catch_up");
                            next_height = connectionThread.getBad() + 1;
                            connectionThread.getBlockchain().set_catch_up(connectionThread.getServer());
                        }
                    } else {
                        if (connectionThread.getBlockchain().get_catch_up() == null && bh < connectionThread.getTip()) {
                            LogUtils.d("catching up from " + (bh + 1));
                            connectionThread.setMode("catch_up");
                            next_height = bh + 1;
                            connectionThread.getBlockchain().set_catch_up(connectionThread.getServer());
                        }
                    }
                }
                notify("updated");
            }
        } else if ("catch_up".equals(connectionThread.getMode())) {
            boolean can_connect = connectionThread.getBlockchain().can_connect(header, true);
            if (can_connect) {
                connectionThread.getBlockchain().save_header(header);
                if (height < connectionThread.getTip()) {
                    next_height = height + 1;
                } else {
                    next_height = 0;
                }
            } else {
                //go back
                LogUtils.d("cannot connect " + height);
                connectionThread.setMode("backward");
                connectionThread.setBad(height);
                connectionThread.setBad_header(header);
                next_height = height - 1;
            }
            if (next_height == 0) {
                // exit catch_up state
                LogUtils.d("catch up done " + connectionThread.getBlockchain().height());
                connectionThread.getBlockchain().set_catch_up(null);
                switch_lagging_interface();
                notify("updated");
                context.setLatestBlock(true);
            }
        } else {
            try {
                throw new Exception("Exception " + connectionThread.getMode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //If not finished, get the next header
        if (next_height != 0) {
            //
            if (connectionThread.getMode().equals("catch_up") && connectionThread.getTip() > next_height + 50) {
                request_chunk(next_height / 2016);
            } else {

                request_header(next_height);
            }
        } else {
            connectionThread.setMode("default");
            connectionThread.setRequest(0);
            notify("updated");
        }
        //refresh network dialog

    }

    private void request_chunk(int idx) {
        queue_request("blockchain.block.get_chunk", new Integer[]{new Integer(idx)});
        connectionThread.setRequest(idx);
    }

    private void notify(String key) throws JSONException {
        if ("status".equals(key)) {

        } else if ("updated".equals(key)) {


        } else {
        }

    }

    public void on_notify_header(JSONObject header) throws JSONException, IOException {
        int height = header.optInt("block_height");
        if (height == 0)
            return;
        connectionThread.setTip_header(header);
        connectionThread.setTip(height);

        if (!"default".equals(connectionThread.getMode())) {
            return;
        }

        Blockchain b = BlockchainsUtil.check_header(header);
        LogUtils.d("old b is " + b);
        if (b != null) {
            connectionThread.setBlockchain(b);
            switch_lagging_interface();
            context.setLatestBlock(true);
            return;
        }
        b = BlockchainsUtil.can_connect(header);

        LogUtils.d("new b is b" + b);
        if (b != null) {
            connectionThread.setBlockchain(b);
            b.save_header(header);
            switch_lagging_interface();
            notify("updated");
            notify("interfaces");
            context.setLatestBlock(true);
            return;
        }
        Object[] obj = blockchains.values().toArray();
        int tip = Integer.MIN_VALUE;
        for (int i = 0; i < obj.length; i++) {
            int tmp = ((Blockchain) obj[i]).height();
            if (tip < tmp) {
                tip = tmp;
            }
        }
        if (tip >= 0) {
            connectionThread.setMode("backward");
            connectionThread.setBad(height);
            connectionThread.setBad_header(header);
            request_header(Math.min(tip, height - 1));
        } else {
            Blockchain chain = (Blockchain) blockchains.get(new Integer(0));
            LogUtils.d("catch up " + chain.get_catch_up());
            if (chain.get_catch_up() == null) {
                chain.set_catch_up(connectionThread);
                connectionThread.setMode("catch_up");
                connectionThread.setBlockchain(chain);
                request_header(0);
            }
        }
    }

    private boolean server_is_lagging() {
        int sh = get_server_height();
        if (sh == 0) {
            LogUtils.d("no height for main interface");
            return true;
        }
        int lh = get_local_height();
        boolean result = (lh - sh) > 1;
        if (result) {
            LogUtils.d(context.getDefaultServer() + " is lagging " + sh + " vs " + lh);
        }
        return result;
    }

    public int get_local_height() {
        return blockchain().height();
    }

    public int get_server_height() {
        if (connectionThread != null)
            return connectionThread.getTip();
        else
            return 0;
    }

    public Blockchain blockchain() {
        LogUtils.d(connectionThread.getBlockchain());
        if (connectionThread != null && connectionThread.getBlockchain() != null) {
            blockchain_index = connectionThread.getBlockchain().get_checkpoint();
        }
        LogUtils.d("blockchain index" + blockchain_index);
        return (Blockchain) blockchains.get(blockchain_index);
    }

    //need to check how we can implement this
    private void switch_lagging_interface() throws JSONException {

		/*if(server_is_lagging()) {
			//switch to one that has the correct header (not height)
			JSONObject header = blockchain().read_header(get_local_height());
			LogUtils.d("header "+header);
			Iterator keys = interfaces.keySet().iterator();
			Set filtered = new HashSet();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				Interface i = (Interface)interfaces.get(key);
				if(i.getTip_header().equals(header)) {
					filtered.add(key);
				}
			}
			if(filtered.size()>0) {
				int random = new Random().nextInt(filtered.size());
				Server choice = (Server)filtered.toArray()[random];
				switch_to_interface(choice);
			}
		}*/
    }

    private void connection_down(Server server) throws JSONException {
        LogUtils.e("connection_down");
        Iterator it = blockchains.values().iterator();
        while (it.hasNext()) {
            Blockchain b = (Blockchain) it.next();
            if (server.equals(b.get_catch_up())) {
                b.set_catch_up(null);
            }
        }
    }

    public String get_index(String method, Object[] params) {
        //   """ hashable index for subscriptions and cache"""
        if (params.length == 0) {
            return method;
        } else {
            return method + ":" + params[0].toString();
        }
    }

	/*private void maintain_requests() throws JSONException {
		Iterator it = interfaces.values().iterator();
		while (it.hasNext()) {
			Interface interface1 = (Interface) it.next();
			if(connectionManager.getRequest() != 0 && (date.getTime() - connectionManager.getRequest_time()) > 20) {
				LogUtils.d("blockchain request timed out");
				connection_down(connectionManager.getServer());
			}
		}
	}*/

    private Set pending_sends = new HashSet();

    public void process_pending_sends() {
        // Requests needs connectivity.  If we don't have an interface,
        // we cannot process them.
        if (context.getTcpConnection() == null)
            return;

        synchronized (pending_sends) {
            Set sends = pending_sends;
            pending_sends = new HashSet();
            Iterator it = sends.iterator();
            while (it.hasNext()) {
                MessagesCallbackTuple mcTuple = (MessagesCallbackTuple) it.next();
                Iterator it1 = mcTuple.getMethodParamsTuple().iterator();
                while (it1.hasNext()) {
                    MethodParamsTuple mptuple = (MethodParamsTuple) it1.next();
                    JSONObject r = null;
                    if (mptuple.getMethod().endsWith(".subscribe")) {
                        String k = get_index(mptuple.getMethod(), mptuple.getParams());
                        //add callback to list
                        Set callbacks = (Set) subscriptions.get(k);
                        if (!callbacks.contains(mcTuple.getCallback())) {
                            callbacks.add(mcTuple.getCallback());
                        }
                        subscriptions.put(k, callbacks);
                        //check cached response for subscriptions
                        r = (JSONObject) sub_cache.get(k);
                    }
                    if (r != null) {
                        mcTuple.getCallback().call(r);
                    } else {
                        message_id = queue_request(mptuple.getMethod(), mptuple.getParams());
                        unanswered_requests.put(new Integer(message_id), new Object[]{mptuple.getMethod(), mptuple.getParams(), mcTuple.getCallback()});
                    }
                }
            }
        }
    }

    private void send_subscriptions() {
        LogUtils.d("sending subscriptions to " + connectionThread.getServer().toString() + " " + unanswered_requests.size() + " " + subscribed_addresses.size());
        sub_cache.clear();
        Object[] requests = unanswered_requests.values().toArray();
        unanswered_requests = new HashMap();
        for (int i = 0; i < requests.length; i++) {
            Object[] request = (Object[]) requests[i];
            int message_id = queue_request((String) request[0], (String[]) request[1]);
            unanswered_requests.put(new Integer(message_id), request);
        }
        queue_request("server.peers.subscribe", new String[]{});
        Iterator it = subscribed_addresses.iterator();
        while (it.hasNext()) {
            String type = (String) it.next();
            queue_request("blockchain.scripthash.subscribe", new String[]{type});
        }
    }

    private void request_header(int height) {
        queue_request("blockchain.block.get_header", new Integer[]{new Integer(height)});
        connectionThread.setRequest(height);
    }

    public int queue_request(String method, Object[] params) {
        connectionThread.addRequest(method, params, message_id);
        return ++message_id;
    }

    public String queue_synch_request(String method, Object[] params) {
        connectionThread.addRequest(method, params, message_id);
        ++message_id;
        while (responseString == "") {
        }
        String response = responseString;
        responseString = "";
        return response;
    }


    public void setDownloadingHeaders(boolean b) {
        this.downloadingHeaders = b;
    }

    public boolean isDownloadingHeaders() {
        return downloadingHeaders;
    }


    public void subscribeAddresses() {
        JSONObject addresses = storage.get("addr_history", new JSONObject());
        Enumeration e = addresses.keys();
        while (e.hasMoreElements()) {
            String address = (String) e.nextElement();
            String scripthash;
            scripthash = AddressUtil.to_scripthash_hex_from_legacy(address);
            scripthashMap.put(scripthash, address);
            queue_request("blockchain.scripthash.subscribe", new String[]{scripthash});
        }
    }

    public void getBalance() {
        String address = Utils.getLegacyAddrUsing();
        String scripthash;
        scripthash = AddressUtil.to_scripthash_hex_from_legacy(address);
        queue_request("blockchain.scripthash.get_balance", new String[]{scripthash});
    }

    public void getFee() {
        queue_request("blockchain.relayfee", new String[]{});
    }

    public void get_history(JSONObject respone) throws JSONException {
        String result = respone.optString("result");
        if (result != null && !result.equals("null")) {
            queue_request("blockchain.scripthash.get_history", new String[]{respone.getJSONArray("params").getString(0)});
        }
    }

    public void get_history() throws JSONException {
        JSONObject addresses = storage.get("addr_history", new JSONObject());
        Enumeration e = addresses.keys();
        while (e.hasMoreElements()) {
            String address = (String) e.nextElement();
            String scripthash;
            scripthash = AddressUtil.to_scripthash_hex_from_legacy(address);
            scripthashMap.put(scripthash, address);
            queue_request("blockchain.scripthash.get_history", new String[]{scripthash});
        }
    }

    public void get_transaction() {
        JSONObject transactions = storage.get("transactions", new JSONObject());
        Enumeration e = transactions.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            queue_request("blockchain.transaction.get", new String[]{key});
        }

    }

    public HashMap getTxHashHeight() throws JSONException {
        HashMap txHashHeight = new HashMap();
        JSONObject jsonObject = storage.get("addr_history", new JSONObject());
        Enumeration e = jsonObject.keys();
        while (e.hasMoreElements()) {
            String object = (String) e.nextElement();
            JSONArray array = jsonObject.getJSONArray(object);
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONArray item = array.getJSONArray(i);

                    txHashHeight.put(item.getString(0), item.getString(1));
                }
            }
        }
        return txHashHeight;
    }

    public void varify(HashMap hashmap) {
        Iterator it = hashmap.keySet().iterator();
        while (it.hasNext()) {
            String hash = (String) it.next();
            String height = (String) hashmap.get(hash);

            queue_request("blockchain.transaction.get_merkle", new Object[]{hash, Integer.valueOf(height)});
        }
    }

    public Set getSubscribedAddresses() {
        return subscribed_addresses;
    }
}

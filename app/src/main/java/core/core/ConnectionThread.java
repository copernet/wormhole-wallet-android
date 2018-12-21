package core.core;

import com.blankj.utilcode.util.LogUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import core.java.util.ArrayList;
import core.java.util.HashMap;
import core.java.util.List;
import core.java.util.Map;
import core.util.Server;

import net.wstech2.me.httpsclient.HttpsConnectionUtils;

public class ConnectionThread extends Thread {
    private TcpConnection tcpConnection;
    private java.util.List unsentRequest;
    private Map unanswered_requests;
    private List answered_response;
    private Blockchain blockchain;
    private int tip;
    private int bad;
    private int good;
    private String mode;
    private JSONObject good_header;
    private JSONObject tip_header;
    private JSONObject bad_header;
    private int request;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;
    private long mLastMsgTime = System.currentTimeMillis();

    public ConnectionThread(TcpConnection tcpConnection) {
        super("ConnectionThread");
        this.tcpConnection = tcpConnection;
        unanswered_requests = new HashMap();
        unsentRequest = new java.util.ArrayList();
        answered_response = new ArrayList();
        try {
            dis = tcpConnection.getInputStream();
            dos = tcpConnection.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        start();
    }

    public Server getServer() {
        return tcpConnection.getServer();
    }

    public void setConnection(TcpConnection tcpConnection) {
        this.tcpConnection = tcpConnection;
    }

    public void addRequest(String method, Object[] params, int message_id) {
        unsentRequest.add(new RequestQueueItem(method, params, message_id));
    }

    public List getResponses() {
        return answered_response;
    }

    public int num_requests() {
		int n = 100 - unanswered_requests.size();
		return Math.min(n, unsentRequest.size());
    }

    public boolean sendRequest() {
        try {
            int n = num_requests();
            StringBuffer stringBuffer = new StringBuffer();
            java.util.Iterator it = unsentRequest.iterator();
            int i = 0;
            while (it.hasNext()) {
                i++;
                if (i > 100) {
                    break;
                }
                RequestQueueItem item = (RequestQueueItem) it.next();
                stringBuffer.append(item.toJsonString() + "\n");
                unanswered_requests.put(item.getId(), item);
                it.remove();
//				unsentRequest.remove(i);
            }
            LogUtils.d("qtest", "sended " + stringBuffer.toString());
            dos.write(stringBuffer.toString().getBytes());
            dos.flush();
        } catch (Exception e) {
            try {
                reconnect();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void run() {

        while (true) {

            try {

                String message = HttpsConnectionUtils.readLine(dis);
                if (message.length() != 0) {
                    mLastMsgTime = System.currentTimeMillis();
                    JSONObject response = new JSONObject(message);
                    if (response.has("id")) {
                        Integer id = new Integer(response.getInt("id"));
                        RequestQueueItem item = (RequestQueueItem) unanswered_requests.get(id);

                        if (item != null) {
                            answered_response.add(new RequetResponseTuple(item, response));
                            unanswered_requests.remove(id);
                        } else {
                            LogUtils.d("unknown wire id " + id);
                        }
                    } else {
                        answered_response.add(new RequetResponseTuple(null, response));
                    }
                } else {
                    if (System.currentTimeMillis() - mLastMsgTime > 60 * 1000) {

                        reconnect();
                    } else {
                        Thread.sleep(3 * 1000);
                    }

//					wait();
                }
            } catch (JSONException je) {
                je.printStackTrace();
            } catch (IOException e) {
                reconnect();
                e.printStackTrace();
            } catch (Exception e) {
                reconnect();
                e.printStackTrace();
            }
        }
    }

    private void reconnect() {
        LogUtils.e("reconnect!");
        //NOTE(qiaochuanbei) 确认阻塞
        try {
            tcpConnection.reconnect();
            dis = tcpConnection.getInputStream();
            dos = tcpConnection.getOutputStream();


            Network.getInstance().queue_request("blockchain.headers.subscribe", new String[]{});
            Network.getInstance().queue_request("server.version", new String[]{"3.1.5", "1.1"});
            Network.getInstance().subscribeAddresses();

//            electrol.java.util.Set keySet = unanswered_requests.keySet();
//            Iterator it = keySet.iterator();
//            while (it.hasNext()) {
//                Integer key = (Integer) it.next();
//                RequestQueueItem value = (RequestQueueItem) unanswered_requests.get(key);
//                unsentRequest.add(value);
//            }
            unanswered_requests.clear();
//            sendRequest();
            synchronized (Thread.currentThread()) {
                Thread.currentThread().wait(700);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setTip_header(JSONObject tip_header) {
        this.tip_header = tip_header;
    }

    public int getTip() {
        return tip;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

    public JSONObject getBad_header() {
        return bad_header;
    }

    public void setBad_header(JSONObject bad_header) {
        this.bad_header = bad_header;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public JSONObject getTip_header() {
        return tip_header;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public JSONObject getGood_header() {
        return good_header;
    }

    public void setGood_header(JSONObject good_header) {
        this.good_header = good_header;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

}

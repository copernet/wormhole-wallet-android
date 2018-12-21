package whc.com.whc_wallet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import whc.com.whc_wallet.adapter.BalanceMainPageAdapter;
import whc.com.whc_wallet.util.Utils;
import whc.com.whc_wallet.view.QrCodeDialog;
import core.core.ApplicationContext;
import core.core.Config;
import core.core.Network;
import core.core.NetworkUtil;
import core.core.NoSecureStorage;
import core.core.TcpConnection;
import core.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wormhole.net.Retrofit;
import wormhole.net.response.Balance;
import wormhole.net.response.BaseResponse;

import static android.content.Context.CLIPBOARD_SERVICE;

public class HomeFragment extends Fragment {

    private TextView mNameTv;
    public static Network network;
    public static NoSecureStorage storage;
    private TextView mAddrTv;
    private View mHideBtn;
    private TextView mBalanceNumTv;
    private View mQrCodeV;
    private View mWalletBtn;
    private View mBurnBtn;
    private TextView mBchBlanceNumTv;
    private TextView mWhcBlanceNumTv;
    private ApplicationContext context;
    private String mReceiveTestAddr;
    private PullLoadMoreRecyclerView mRecyclerView;
    private BalanceMainPageAdapter mAdapter;
    private BigDecimal mWhcBlance = new BigDecimal(0);
    private View mBchLy;
    private View mWhcLy;
    private String mWalletName;
    private String mReceiveAddr;

    private boolean showBalance = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main_t, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
        getDatas();
        refreshViews();
    }

    private void getDatas() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }).start();
        fetchNet();
    }

    public void fetchNet() {
        String strs[] = new String[]{Utils.getNewStyleAddressUsing()};
        Call<BaseResponse<Map<String, ArrayList<Balance>>>> call =  Retrofit.getRetrofit().getService().getBalance(strs);
        call.enqueue(new Callback<BaseResponse<Map<String, ArrayList<Balance>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<Map<String, ArrayList<Balance>>>> call, Response<BaseResponse<Map<String, ArrayList<Balance>>>> response) {
                mRecyclerView.setPullLoadMoreCompleted();
                if (!Utils.dealCommonNetRes(response)) return;
                Set<String> keys = response.body().result.keySet();

                Iterator<String> it1 = keys.iterator();
                while (it1.hasNext()) {
                    ArrayList<Balance> subList = response.body().result.get(it1.next());
                    List<Balance> showlist = subList;
                    if (subList.size() > 0 && "whc".equals(subList.get(0).property_name.toLowerCase())) {
                        showlist = subList.subList(1, subList.size());
                        mWhcBlance = subList.get(0).balance_available;

                    } else {
                        mWhcBlance = new BigDecimal(0);
                    }
                    mBalanceNumTv.setText(showBalance ? "" + mWhcBlance : "***");
                    mWhcBlanceNumTv.setText(showBalance ? "" + mWhcBlance : "***");

                    mAdapter.updateData(showlist);
//                    mRecyclerView.setAdapter(mAdapter);
                }
//                showList();

            }

            @Override
            public void onFailure(Call<BaseResponse<Map<String, ArrayList<Balance>>>> call, Throwable t) {
                mRecyclerView.setPullLoadMoreCompleted();
            }
        });
    }


    private <T extends View> T findViewById(int id) {
        return getActivity().findViewById(id);
    }


    @Override
    public void onResume() {
        super.onResume();
        String currWalName = Utils.getCurrentWalletName();
        if (!currWalName.equals(mWalletName)) {
            bindViews();
            getDatas();
            refreshViews();
        }
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            String currWalName = Utils.getCurrentWalletName();
//            if (!currWalName.equals(mWalletName)) {
//                bindViews();
//                getDatas();
//            }
//        }
//    }

    private void bindViews() {
        mWalletName = Utils.getCurrentWalletName();
        try {
            storage = NoSecureStorage.getInstance(Constants.STORAGE_NO_SECURE_PATH + mWalletName.hashCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mNameTv = (TextView) findViewById(R.id.nameTv);
        mAddrTv = (TextView) findViewById(R.id.addrTv);
        mHideBtn = (View) findViewById(R.id.hideBtn);
        mBalanceNumTv = (TextView) findViewById(R.id.balanceNumTv);
        mBurnBtn = (View) findViewById(R.id.burnBtn);
        mBchBlanceNumTv = (TextView) findViewById(R.id.bchBlanceNumTv);
        mWhcBlanceNumTv = (TextView) findViewById(R.id.whcBlanceNumTv);
        mBchLy = findViewById(R.id.bchLy);
        mWhcLy = findViewById(R.id.whcLy);
        mQrCodeV = (View) findViewById(R.id.qrCodeV);
        mWalletBtn = (View) findViewById(R.id.walletBtn);

        mRecyclerView = (PullLoadMoreRecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setPushRefreshEnable(false);
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                fetchNet();

            }

            @Override
            public void onLoadMore() {

            }
        });

        mRecyclerView.setLinearLayout();
        mAdapter = new BalanceMainPageAdapter(new ArrayList<Balance>(), getActivity());
        mRecyclerView.setAdapter(mAdapter);
        // 设置adapter
        mQrCodeV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QrCodeDialog dialog = new QrCodeDialog(getActivity(), mWalletName, mReceiveAddr);
                dialog.show();
            }
        });
        mBurnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), BurnActivityT.class);
                startActivity(it);
            }
        });
        mAddrTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ((TextView)v).getText().toString();
                ClipData myClip = ClipData.newPlainText("text", text);
                // 获取系统剪贴板
                ClipboardManager myClipboard =
                        (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                myClipboard.setPrimaryClip(myClip);
                ToastUtils.showLong(R.string.copy_already);
            }
        });
        mBchLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(getActivity(), FundHistrotyActivity.class);
                Balance funding = new Balance();
                funding.property_id = 0;
                funding.property_name = "BCH";
                funding.balance_available = Network.sConfirmedMoney.add(Network.sUnConfirmedMoney).divide(new BigDecimal(100000000));
                it.putExtra(FundHistrotyActivity.EXTRA_FUNDING, new Gson().toJson(funding));
                getActivity().startActivity(it);
            }
        });
        // TODO这段代码因为后台无数据，暂时关闭。正式网打开
//        mBchLy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it = new Intent(getActivity(), FundHistrotyActivity.class);
//                Balance funding = new Balance();
//                funding.property_id = 0;
//                funding.property_name = "BCH";
//                funding.balance_available = Float.valueOf(mBchBlanceNumTv.getText().toString());
//                it.putExtra(FundHistrotyActivity.EXTRA_FUNDING, new Gson().toJson(funding));
//                getActivity().startActivity(it);
//            }
//        });
        mWhcLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), FundHistrotyActivity.class);
                Balance funding = new Balance();
                funding.property_id = 1;
                funding.property_name = "WHC";
                funding.balance_available = mWhcBlance;
                it.putExtra(FundHistrotyActivity.EXTRA_FUNDING, new Gson().toJson(funding));
                getActivity().startActivity(it);
            }
        });
        mWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ManageWalletActivity.class);
                startActivity(it);
            }
        });
        mHideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBalance = !showBalance;
                refreshViews();
            }
        });

    }

    private void refreshViews() {
        mNameTv.setText(mWalletName);
        Set<String> names = Utils.getWalletNameList(getActivity());
        if (!names.contains(mWalletName)) {
            names.add(mWalletName);
            Utils.setWalletNameList(getActivity(), names);
        }
        try {
            JSONObject addresses = storage.get("addresses", new JSONObject());
            JSONArray array = addresses.optJSONArray(Constants.STORAGE_ADDR_RECEIVE_NEW);
            mReceiveAddr = array.get(0).toString();
            mAddrTv.setText(mReceiveAddr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            mWhcBlanceNumTv.removeCallbacks(runnable);
        } catch (Exception e) {

        }
        mWhcBlanceNumTv.postDelayed(runnable, 5 * 1000);
        mBalanceNumTv.setText(showBalance ? "" + mWhcBlance : "***");
        mWhcBlanceNumTv.setText(showBalance ? "" + mWhcBlance : "***");
        String amountBch = Network.sConfirmedMoney.add(Network.sUnConfirmedMoney).divide(new BigDecimal(100000000)).toString();
        mBchBlanceNumTv.setText(showBalance ? "" + amountBch : "***");
        mAdapter.setShowBalance(showBalance);

    }

//    public void checkOrGenerateAddress() throws JSONException {
//        JSONObject jsonObject = storage.get("keystore", new JSONObject());
//        if(jsonObject.length() == 0) {
//            Main main = new Main();
//            String keys[] = main.getKeys(storage.get("seed", ""));
//            jsonObject.put("xprv", keys[0]);
//            jsonObject.put("xpub", keys[1]);
//            storage.put("keystore", jsonObject);
//        }
//        JSONObject addresses = storage.get("addresses", new JSONObject());
//        JSONObject addr_history = storage.get("addr_history", new JSONObject());
//        JSONArray change = addresses.optJSONArray("change");
//        JSONArray changeTestNet = addresses.optJSONArray("changeTestNet");
//        if(change== null || change.length() == 0) {
//            AddressUtil addressUtil = new AddressUtil(jsonObject.getString("xpub"));
//            String[] changeAddresses;
//            changeAddresses = addressUtil.generateLegacyAddresses(1, AddressUtil.ADDRESS_TYPE_CHANGE);
//            change = new JSONArray();
//            for(int i =0 ;i<changeAddresses.length;i++) {
//                change.put(changeAddresses[i]);
//                addr_history.put(changeAddresses[i], new JSONArray());
//            }
//
//            String[] changeAddressesTest;
//            changeAddressesTest = addressUtil.generateCashAddresses(1, AddressUtil.ADDRESS_TYPE_CHANGE);
//            changeTestNet = new JSONArray();
//            for(int i =0 ;i<changeAddressesTest.length;i++) {
//                changeTestNet.put(changeAddressesTest[i]);
//            }
//
//            addresses.put("change", change);
//            addresses.put("changeTestNet", changeTestNet);
//            storage.put("addresses", addresses);
//        }
//        JSONArray receiving = addresses.optJSONArray("receiving");
//        JSONArray receivingTestNet = addresses.optJSONArray("receivingTestNet");
//        if(receiving== null || receiving.length() == 0) {
//            AddressUtil addressUtil = new AddressUtil(jsonObject.getString("xpub"));
//            String[] receivingAddresses;
//            receivingAddresses = addressUtil.generateLegacyAddresses(1, AddressUtil.ADDRESS_TYPE_RECEIVING);
//            receiving = new JSONArray();
//            for(int i =0 ;i<receivingAddresses.length;i++) {
//                receiving.put(receivingAddresses[i]);
//                addr_history.put(receivingAddresses[i], new JSONArray());
//            }
//            addresses.put("receiving", receiving);
//
//            String[] receivingAddressesTestNet;
//            receivingAddressesTestNet = addressUtil.generateCashAddresses(1, AddressUtil.ADDRESS_TYPE_RECEIVING);
//            receivingTestNet = new JSONArray();
//            for(int i =0 ;i<receivingAddressesTestNet.length;i++) {
//                receivingTestNet.put(receivingAddressesTestNet[i]);
//            }
//            addresses.put("receivingTestNet", receivingTestNet);
//            storage.put("addr_history", addr_history);
//            storage.put("addresses", addresses);
//        }
//        mReceiveTestAddr = receivingTestNet.get(0).toString();
//        final JSONArray finalReceiving = receiving;
//        final JSONArray finalChange = change;
//        mWhcBlanceNumTv.post(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
////                    mAddrTv.setText(finalChange.get(0).toString());
//                    mAddrTv.setText(finalReceiving.get(0).toString());
//                    float amountBch = getBalance(storage);
//                    mBchBlanceNumTv.setText("" + amountBch);
////                    seedView.setText(storage.get("seed", ""));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                mWhcBlanceNumTv.postDelayed(this, 500);
//            }
//        });
//        storage.write();
//    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

//                String amountBch = getBalance(storage).toString();
            String amountBch = Network.sConfirmedMoney.add(Network.sUnConfirmedMoney).divide(new BigDecimal(100000000)).toString();
            mBchBlanceNumTv.setText(showBalance ? "" + amountBch : "***");
            mWhcBlanceNumTv.postDelayed(this, 20 * 1000);
        }
    };

    public void start() {

        context = ApplicationContext.getInstance();
        context.setLatestBlock(false);

        Config config = new Config();
        context.setDefaultServer(Constants.getHost() + ":9629:t");
        context.setConfig(config);
        try {
            TcpConnection tcp = new TcpConnection(NetworkUtil.deserialize_server(context.getDefaultServer()));
            context.setTcpConnection(tcp);
            network = new Network(context,storage);
            network.initHeaderFile();
            network.start();
//            network.queue_request("blockchain.transaction.get_merkle", new Object[] {"2a0fd8bf6c9c4f4ef1dd2e60e445ffc0485feec81e3402656f53465db42a8b95", 1253106});
//            pay();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static long getDelta(NoSecureStorage store, String hash, int amt) throws JSONException {
        JSONObject txi = store.get("txi", new JSONObject());
        JSONObject txo = store.get("txo", new JSONObject());
        JSONObject txiOut = txi.getJSONObject(hash);
        Enumeration e = txiOut.keys();
        int inAmount = 0;
        int outAmount = 0;
        while (e.hasMoreElements()) {
            JSONArray array = txiOut.getJSONArray(e.nextElement().toString());
            for (int i = 0; i < array.length(); i++) {
             JSONArray nested = array.getJSONArray(i);
            inAmount += nested.optInt(1);
            }
        }
        JSONObject txoOut = txo.getJSONObject(hash);
        Enumeration e1 = txoOut.keys();
        while (e1.hasMoreElements()) {

            JSONArray array = txoOut.getJSONArray(e1.nextElement().toString());
            for (int i = 0; i < array.length(); i++) {
                JSONArray nested = array.getJSONArray(i);
                outAmount += nested.optInt(1);
            }

        }
        return outAmount - inAmount;
    }

    public static BigDecimal getBalance(NoSecureStorage storage) throws JSONException {
        JSONObject object = storage.get("txo", new JSONObject());
        Enumeration e = object.keys();
        long amount = 0;
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            long tx_delta = getDelta(storage, key, 0);
            amount += tx_delta;
        }
        BigDecimal bigDecimal = new BigDecimal(amount);
        bigDecimal = bigDecimal.divide(new BigDecimal(100000000));
        return bigDecimal;
    }

}

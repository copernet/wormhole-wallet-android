package whc.com.whc_wallet.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.BaseObservable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import whc.com.whc_wallet.MainActivityT;
import whc.com.whc_wallet.TxNoticeFragment;
import core.ReceivingAddress;
import core.core.AddressUtil;
import core.core.Bitcoin;
import core.core.Coinchooser;
import core.core.Network;
import core.core.NoSecureStorage;
import core.core.Storage;
import core.core.Transaction;
import core.core.TxIn;
import core.core.TxOut;
import core.core.WalletAddress;
import core.util.BigInteger;
import core.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import whc.com.whc_wallet.R;
import wormhole.net.Retrofit;
import wormhole.net.response.BasePageResponse;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.GetFee;
import wormhole.net.response.Notice;

/**
 * Created by chuanbei.qiao on 2018/10/25.
 */

public class Utils {
    /**
     * 将实体类转换成请求参数,以map<k,v>形式返回
     *
     * @return
     */
    public static Map<String, Object> getMapParams(Object object) {
        Class clazz = object.getClass();
        Class<? extends Object> superclass = clazz.getSuperclass();

        Field[] fields = clazz.getDeclaredFields();
        Field[] superFields = superclass.getDeclaredFields();

        if (fields == null || fields.length == 0) {
            return Collections.emptyMap();
        }

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (!"null".equals(String.valueOf(field.get(object)))) {
                    params.put(field.getName(), String.valueOf(field.get(object)));
                }
            }

            if (superclass != Object.class && superclass != BaseObservable.class) {
                for (Field superField : superFields) {
                    superField.setAccessible(true);
                    if (!"null".equals(String.valueOf(superField.get(object)))) {
                        params.put(superField.getName(), String.valueOf(superField.get(object)));
                    }
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return params;
    }

    public static String getTestAddress() {
        //HACK(qiaochuanbei) 使用hack地址
        if (true) {
            return getNewStyleAddressUsing();
//            return "bchtest:qq6qag6mv2fzuq73qanm6k60wppy23djnv7ddk3lpk";
        }

        try {
            NoSecureStorage storage = NoSecureStorage.getInstance(getNoSecureStoragePathCurrent());
            JSONObject addresses = storage.get("addresses", new JSONObject());
            JSONArray array = addresses.optJSONArray(Constants.STORAGE_ADDR_RECEIVE_NEW);
            String mReceiveAddr = array.get(0).toString();
            return mReceiveAddr;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getNewStyleAddressUsing() {

        try {
            NoSecureStorage storage = NoSecureStorage.getInstance(getNoSecureStoragePathCurrent());
            JSONObject addresses = storage.get("addresses", new JSONObject());
            JSONArray array = addresses.optJSONArray(Constants.STORAGE_ADDR_RECEIVE_NEW);
            String mReceiveAddr = array.get(0).toString();
            return mReceiveAddr;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getLegacyAddrUsing() {

        try {
            NoSecureStorage storage = NoSecureStorage.getInstance(getNoSecureStoragePathCurrent());
            JSONObject addresses = storage.get("addresses", new JSONObject());
            JSONArray array = addresses.optJSONArray("receiving");
            String mReceiveAddr = array.get(0).toString();
            return mReceiveAddr;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getCurrentWalletName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String walletName = sharedPreferences.getString(Constants.STORAGE_WALLET_NAME, "");
        return walletName;
    }

    public static String getCurrentWalletName() {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String walletName = sharedPreferences.getString(Constants.STORAGE_WALLET_NAME, "");
        return walletName;
    }

    public static void setCurrentWalletName(Context context, String walletName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.STORAGE_WALLET_NAME, walletName);
        editor.commit();
    }

    public static void setAddrOfWallet(String name, String addr) {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.STORAGE_WALLET_NAME + name, addr);
        editor.commit();
    }

    public static String getLegacyAddrByWalletName(String name) {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String walletName = sharedPreferences.getString(Constants.STORAGE_WALLET_NAME + name, "");
        return walletName;
    }

    public static String getNewStyleAddrByWalletName(String name) {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String walletName = sharedPreferences.getString(Constants.STORAGE_WALLET_NAME + name, "");
        return walletName;
    }

    public static Set<String> getWalletNameList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        Set<String> walletName = sharedPreferences.getStringSet(Constants.STORAGE_WALLET_NAME_LIST, new HashSet<String>());
        return walletName;
    }

    public static void setWalletNameList(Context context, Set<String> walletNameList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(Constants.STORAGE_WALLET_NAME_LIST, walletNameList);
        editor.commit();
    }

    public static String getSecureStoragePathCurrent() {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        String walletName = getCurrentWalletName(context);
        return Constants.STORAGE_PATH + walletName.hashCode();

    }

    public static String getNoSecureStoragePathCurrent() {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        String walletName = getCurrentWalletName(context);
        return Constants.STORAGE_NO_SECURE_PATH + walletName.hashCode();

    }

    public static void saveFee(GetFee result) {
        SharedPreferences sharedPreferences = com.blankj.utilcode.util.Utils.getApp().getSharedPreferences(com.blankj.utilcode.util.Utils.getApp().getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(result);
        editor.putString(Constants.STORAGE_FEE, json);
        editor.commit();
    }

    public static void updateNoticeTime() {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Constants.STORAGE_NOTICE_TIME, System.currentTimeMillis() / 1000);
        editor.commit();
    }

    public static long getNoticeTime() {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        long lastSawNoticeTime = sharedPreferences.getLong(Constants.STORAGE_NOTICE_TIME, 1541141660l);
        return lastSawNoticeTime;
    }

    public static GetFee getFee() {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String walletName = sharedPreferences.getString(Constants.STORAGE_FEE, null);
        Gson gson = new Gson();
        GetFee json = gson.fromJson(walletName, GetFee.class);
        return json;
    }

    public static abstract class PayResCallback {
        public abstract void onRes(String result);
    }

    public static boolean pay(String address, String payAmount, Storage storage, PayResCallback payResCallback, long fee) {
        if (AddressUtil.addressIsValid(address)) {
            try {
                TxOut out = new TxOut(address, new BigInteger(payAmount));
                core.java.util.Set sout = new core.java.util.HashSet();
                sout.add(out);
                ReceivingAddress receivingAddress = new ReceivingAddress();
                core.java.util.Set changeSet = new core.java.util.HashSet();
                changeSet.add(receivingAddress.get_address(storage, false));
                core.java.util.Set txin = get_txin(storage);
                Transaction tx = new Coinchooser(storage).make_tx(txin, sout, changeSet, fee);
                String txHash = tx.Generate();
                String response = Network.getInstance().queue_synch_request("blockchain.transaction.broadcast", new String[]{txHash});
                payResCallback.onRes(response);
                return true;
            } catch (JSONException e) {
                ToastUtils.showLong("tx failed! " + e.getMessage());
                LogUtils.e("Error: " + e.getMessage());
                return false;
            } catch (InvalidCipherTextException e) {
                ToastUtils.showLong("tx failed! " + e.getMessage());
                LogUtils.e("Password is not correct");
                return false;
            } catch (IOException e) {
                ToastUtils.showLong("tx failed! " + e.getMessage());
                LogUtils.e("Error: " + e.getMessage());
                return false;
            } catch (Exception e) {
                ToastUtils.showLong("tx failed! " + e.getMessage());
                LogUtils.e("Error: " + e.getMessage());
                return false;
            }
        } else {
            LogUtils.e("Invalid Receiver Address!!!");
            return false;
        }
    }

    public static core.java.util.Set get_txin(Storage storage) throws JSONException {
        core.java.util.Set set = new core.java.util.HashSet();
        JSONObject verified_tx3 = storage.get("verified_tx3", new JSONObject());
        JSONObject txo = storage.get("txo", new JSONObject());
        JSONObject txi = storage.get("txi", new JSONObject());
        Enumeration e1 = txi.keys();
        core.java.util.Set txiKeySet = new core.java.util.HashSet();
        while (e1.hasMoreElements()) {
            String key = (String) e1.nextElement();
            JSONObject obj = txi.getJSONObject(key);
            Enumeration e2 = obj.keys();
            while (e2.hasMoreElements()) {
                String k = (String) e2.nextElement();
                JSONArray jo = obj.getJSONArray(k);
                if (null != jo && jo.length() > 0) {
                    for (int i = 0; i < jo.length(); i++) {
                        JSONArray nested = jo.getJSONArray(i);
                        String[] strs = (nested.get(0).toString()).split(":");
                        txiKeySet.add(strs[0]);
                    }
                }
            }
        }

        JSONObject addresses = storage.get("addresses", new JSONObject());
        JSONObject keystore = storage.get("keystore", new JSONObject());

//        JSONArray changeArray = addresses.getJSONArray("change");
        JSONArray receivingArray = addresses.getJSONArray("receiving");

        String masterXPUB = keystore.getString("xpub");
        Enumeration tx = verified_tx3.keys();
        while (tx.hasMoreElements()) {
            String key = (String) tx.nextElement();
            JSONObject txoElements = txo.getJSONObject(key);
            Enumeration e = txoElements.keys();
            while (e.hasMoreElements()) {
                String add = (String) e.nextElement();
                if (!txiKeySet.contains(key)) {
                    JSONArray array1 = txoElements.getJSONArray(add);
                    //LogUtils.d(array1);
                    for (int i = 0; i < array1.length(); i++) {
                        JSONArray nestedArray = array1.getJSONArray(i);
                        boolean change = true;
//                    int index = getIndex(changeArray, add);
                        int index = -1;
                        String pubkey;
                        if (index == -1) {
                            change = false;
                        }
                        if (!change) {
                            index = getIndex(receivingArray, add);
                            pubkey = Bitcoin.fetchPublicKey(masterXPUB, AddressUtil.ADDRESS_TYPE_RECEIVING, index);
                        } else {
                            pubkey = Bitcoin.fetchPublicKey(masterXPUB, AddressUtil.ADDRESS_TYPE_CHANGE, index);
                        }

                        WalletAddress walletAddress1 = new WalletAddress(add, index, change, pubkey);
                        TxIn txin = new TxIn(walletAddress1, key, nestedArray.optInt(0), new BigInteger(nestedArray.optString(1)));
                        set.add(txin);
                    }
                }
            }
        }
        return set;
    }


    public static int getIndex(JSONArray addresses, String address) {
        for (int i = 0; i < addresses.length(); i++) {
            if (addresses.optString(i).equals(address)) {
                return i;
            }
        }
        return -1;
    }

    public static void fetchNotices() {

        HashMap<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("address", getNewStyleAddressUsing());
        fieldMap.put("from", getNoticeTime());
        fieldMap.put("to", System.currentTimeMillis() / 1000);

        Call<BaseResponse<BasePageResponse<Notice>>> call = Retrofit.getRetrofit().getService().getNotice(fieldMap);
        call.enqueue(new Callback<BaseResponse<BasePageResponse<Notice>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BasePageResponse<Notice>>> call, Response<BaseResponse<BasePageResponse<Notice>>> response) {
                if (!Utils.dealCommonNetRes(response)) return;
                TxNoticeFragment.sNoticeList = response.body().result.list;
                if (null == TxNoticeFragment.sNoticeList)
                    TxNoticeFragment.sNoticeList = new ArrayList<>();
                if (null != TxNoticeFragment.sNoticeList && TxNoticeFragment.sNoticeList.size() > 0) {
                    MainActivityT.sMainActivity.showNoticeRedIcon();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BasePageResponse<Notice>>> call, Throwable t) {

            }

        });
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        String regStr = "(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+$).{8,}";
        pattern = Pattern.compile(regStr);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static void setLanguage(String lan) {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.LANGUAGE, lan);
        editor.commit();
    }

    public static String getLanguage() {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String language = sharedPreferences.getString(Constants.LANGUAGE, "en");
        return language;
    }

    public static void initLocaleLanguage(Context context) {
        String lan = getLanguage();
        Locale locale = new Locale(lan, "");
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        if ("zh".equals(lan)) {
            configuration.locale = Locale.CHINESE;
        } else {
            configuration.locale = Locale.ENGLISH;
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());//更新配置
    }

    public static <T> boolean dealCommonNetRes(Response<BaseResponse<T>> response) {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        if (null == response || null == response.body() || 0 != response.body().code || null == response.body().result) {
            ToastUtils.showLong(context.getString(R.string.request_error_tip));
            return false;
        } else {
            return true;
        }
    }

    public static <T> boolean dealPayRes(Response<JsonElement> response) {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        if (null == response || null == response.body() || 0 != response.body().getAsJsonObject().get("code").getAsInt()) {
            ToastUtils.showLong(context.getString(R.string.tx_failed_retry));
            return false;
        } else {
            return true;
        }
    }


}

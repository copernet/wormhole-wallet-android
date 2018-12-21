package wormhole.controllers;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.JsonElement;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import core.ReceivingAddress;
import core.java.util.HashSet;
import core.java.util.LinkedHashSet;
import core.java.util.Set;
import core.core.AddressUtil;
import core.core.Bitcoin;
import core.core.Deserialize;
import core.core.RawOutput;
import core.core.Storage;
import core.core.Transaction;
import core.core.TxIn;
import core.core.TxOut;
import core.core.WalletAddress;
import core.util.BigInteger;
import core.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import wormhole.net.Retrofit;
import wormhole.net.response.SignData;

/**
 * Created by chuanbei.qiao on 2018/9/28.
 */

public class TradeAction {

    public static boolean pay(Storage storage, String unsignedStr, String showAddr, List<SignData> sign_data, Callback<JsonElement> callback) {
        Deserialize d = Deserialize.parse(unsignedStr);
        Set sout = new LinkedHashSet();
        try {

            Vector outputs = d.getOutputs();
            for(int i=0; i< d.getOutputCount();i++) {
                RawOutput output = (RawOutput)outputs.elementAt(i);
                JSONArray array = new JSONArray();
                array.put(i);
                array.put(output.getAmount());

                try {
                    String address = AddressUtil.getLegacyAddressByScripthash(output.getScript());
                    TxOut out = new TxOut(address, new BigInteger("" + output.getAmount()));
                    out.setRawScript(output.getScript());
                    sout.add(out);
                    int a = -1;
                    int b = a+1;
                }
                catch(Exception e) {
                    LogUtils.d(outputs);
                }
            }

            ReceivingAddress receivingAddress = new ReceivingAddress();
            Set changeSet = new HashSet();
            changeSet.add(receivingAddress.get_address(storage, false));
            Set txin = get_txin(storage, d, showAddr, sign_data);
            Transaction tx = Transaction.from_io(txin, sout, storage);
            String txHash = tx.Generate();
            Call<JsonElement> call = Retrofit.getRetrofit().getService().broadcast(txHash);
            call.enqueue(callback);
            return true;
        } catch (JSONException e) {
            LogUtils.d("Error: " + e.getMessage());
            return false;
        }  catch (Exception e) {
            LogUtils.d("Error: " + e.getMessage());
            return false;
        }
    }


    //NOTE(qiaochuanbei) nouse now
//    public static boolean pay(String unsignedStr, String showAddr, List<SignData> sign_data, Callback<JsonElement> callback) {
//        Deserialize d = Deserialize.parse(unsignedStr);
//        Set sout = new LinkedHashSet();
//        try {
//
//            Storage storage = new Storage(Constants.STORAGE_PATH, PassWordActivity.password);
//
//            Vector outputs = d.getOutputs();
//            for(int i=0; i< d.getOutputCount();i++) {
//                RawOutput output = (RawOutput)outputs.elementAt(i);
//                JSONArray array = new JSONArray();
//                array.put(i);
//                array.put(output.getAmount());
//
//                try {
//                    String address = AddressUtil.getLegacyAddressByScripthash(output.getScript());
//                    TxOut out = new TxOut(address, new BigInteger("" + output.getAmount()));
//                    out.setRawScript(output.getScript());
//                    sout.add(out);
//                    int a = -1;
//                    int b = a+1;
//                }
//                catch(Exception e) {
//                    LogUtils.d(outputs);
//                }
//            }
//
//            ReceivingAddress receivingAddress = new ReceivingAddress();
//            Set changeSet = new HashSet();
//            changeSet.add(receivingAddress.get_address(storage, true));
//            Set txin = get_txin(storage, d, showAddr, sign_data);
//            Transaction tx = Transaction.from_io(txin, sout, storage);
//            String txHash = tx.Generate();
//            Call<JsonElement> call = Retrofit.getRetrofit().getService().broadcast(txHash);
//            call.enqueue(callback);
//            return true;
//        } catch (JSONException e) {
//            LogUtils.d("Error: " + e.getMessage());
//            return false;
//        } catch (InvalidCipherTextException e) {
//            LogUtils.d("Password is not correct");
//            return false;
//        } catch (IOException e) {
//            LogUtils.d("Error: " + e.getMessage());
//            return false;
//        } catch (Exception e) {
//            LogUtils.d("Error: " + e.getMessage());
//            return false;
//        }
//    }

    private static Set get_txin(Storage storage, Deserialize d, String selectedAddr, List<SignData> sign_data) {
        Set set = new HashSet();
        try {
            JSONObject addresses = storage.get("addresses", new JSONObject());
            JSONArray receivingTestNet = addresses.optJSONArray(Constants.STORAGE_ADDR_RECEIVE_NEW);
            JSONArray changeTestNet = addresses.optJSONArray(Constants.STORAGE_ADDR_CHANGE_NEW);
            boolean isChange = false;
            int index = AddressUtil.getIndex(receivingTestNet, selectedAddr);
            if (index < 0) {
                isChange = true;
                index = AddressUtil.getIndex(changeTestNet, selectedAddr);
            }

            JSONArray receivingArray = addresses.getJSONArray("receiving");
            String addr = receivingArray.get(index).toString();
//
//            JSONArray changeArray = addresses.getJSONArray("change");
//            JSONArray receivingArray = addresses.getJSONArray("receiving");
//            String addr = isChange ? changeArray.getString(index).toString() : receivingArray.get(index).toString();

            JSONObject keystore = storage.get("keystore", new JSONObject());
            String masterXPUB = keystore.getString("xpub");

            String pubkey = Bitcoin.fetchPublicKey(masterXPUB, isChange ? AddressUtil.ADDRESS_TYPE_CHANGE : AddressUtil.ADDRESS_TYPE_RECEIVING, index);

            for (int i=0;i< sign_data.size();i++) {
                WalletAddress walletAddress1 = new WalletAddress(addr, index, isChange, pubkey);
                long value = sign_data.get(i).value.multiply(new BigDecimal(100000000)).longValue();
                TxIn txin = new TxIn(walletAddress1, sign_data.get(i).txid, sign_data.get(i).vout, new BigInteger("" + value));
                set.add(txin);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return set;
    }


}

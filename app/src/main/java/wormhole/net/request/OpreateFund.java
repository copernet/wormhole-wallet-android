package wormhole.net.request;

import android.databinding.BaseObservable;

/**
 * Created by chuanbei.qiao on 2018/10/25.
 */

public class OpreateFund extends BaseObservable {
    public String transaction_version = "0";
    public String fee;
    public String transaction_from;
    public String amount;
    public String amount_to_transfer;
    public String note;
    public String currency_identifier;
    public String transaction_to;
    public String propertyid;
    public String distributionproperty;
    public String ecosystem = "1";

}

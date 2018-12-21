package wormhole.net.request;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import whc.com.whc_wallet.BR;

/**
 * Created by chuanbei.qiao on 2018/10/25.
 */

public class BurnBch extends BaseObservable {
    public int transaction_version;
    public String transaction_from;
    public String amount_for_burn;
    public String fee;

    @Bindable
    public int getTransaction_version() {
        return transaction_version;
    }

    public void setTransaction_version(int transaction_version) {
        this.transaction_version = transaction_version;
        notifyPropertyChanged(BR.transaction_version);
    }

    @Bindable
    public String getTransaction_from() {
        return transaction_from;
    }

    public void setTransaction_from(String transaction_from) {
        this.transaction_from = transaction_from;
        notifyPropertyChanged(BR.transaction_from);
    }

    @Bindable
    public String getAmount_for_burn() {
        return amount_for_burn;
    }

    public void setAmount_for_burn(String amount_for_burn) {
        this.amount_for_burn = amount_for_burn;
        notifyPropertyChanged(BR.amount_for_burn);
    }

    @Bindable
    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
        notifyPropertyChanged(BR.fee);
    }
}

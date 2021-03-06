package whc.com.whc_wallet;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonElement;

import java.util.Map;

import whc.com.whc_wallet.databinding.ActivitySendallBinding;
import whc.com.whc_wallet.util.Utils;
import whc.com.whc_wallet.view.PassWordDialog;
import core.core.Storage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wormhole.controllers.TradeAction;
import wormhole.net.Retrofit;
import wormhole.net.request.OpreateFund;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.CommonResult;

public class SendAllActivity extends BaseActivity {

    private OpreateFund mOpreateFund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySendallBinding activitySeoBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_sendall, mContentLyBase, true);

        mOpreateFund = new OpreateFund();
        mOpreateFund.transaction_from = Utils.getTestAddress();
        activitySeoBinding.setOperateFund(mOpreateFund);

        bindViews();
    }


    private void bindViews() {
        setTilte(R.string.send_all);

        findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassWordDialog dialog = new PassWordDialog(SendAllActivity.this);
                if (TextUtils.isEmpty(mOpreateFund.fee)
                        || TextUtils.isEmpty(mOpreateFund.transaction_to)) {
                    ToastUtils.showLong(R.string.enter_need_first);
                    return;
                }
                double fee = new Double(mOpreateFund.fee);
                final long feeL = (long) (fee * 100000000);
                if (fee > 0.2) {
                    ToastUtils.showLong(R.string.fee_invaliad);
                    return;
                }
                dialog.setOnDismissListener(dialog.new PwdDismissListener() {
                    @Override
                    public void onGetStorage(Storage storage) {
                        if (null == storage) return;
                        Utils.PayResCallback callback = new Utils.PayResCallback() {
                            @Override
                            public void onRes(String result) {
                                ToastUtils.showLong(R.string.tx_successed);
                                finish();
                            }
                        };
                        doTrade(storage);
                    }
                });
                dialog.show();
            }
        });

    }

    private void doTrade(final Storage storage) {

        Map filedMap = Utils.getMapParams(mOpreateFund);
        Call<BaseResponse<CommonResult>> call = Retrofit.getRetrofit().getService().sendAll(filedMap);
        call.enqueue(new Callback<BaseResponse<CommonResult>>() {
            @Override
            public void onResponse(Call<BaseResponse<CommonResult>> call, Response<BaseResponse<CommonResult>> response) {
                Callback<JsonElement> callback = new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        if (!Utils.dealPayRes(response)) return;
                        ToastUtils.showLong(R.string.tx_successed);
                        SendAllActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                };
                if (!Utils.dealCommonNetRes(response)) return;
                TradeAction.pay(storage, response.body().result.unsigned_tx, mOpreateFund.transaction_from , response.body().result.sign_data, callback);

            }

            @Override
            public void onFailure(Call<BaseResponse<CommonResult>> call, Throwable t) {

            }
        });
    }


}

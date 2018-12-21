package whc.com.whc_wallet;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Map;

import whc.com.whc_wallet.databinding.ActivityJoinFundBinding;
import whc.com.whc_wallet.util.Utils;
import whc.com.whc_wallet.view.PassWordDialog;
import core.core.Storage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wormhole.controllers.TradeAction;
import wormhole.net.Retrofit;
import wormhole.net.request.JoinFund;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.CommonResult;
import wormhole.net.response.PropertyData;

public class JoinCrowdActivityT extends BaseActivity {
    public static final String EXTRA_FUNDING = "extra_funding";

    private JoinFund burnBch;
    private PropertyData mFunding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTilte(R.string.join_crowd);
        String fundingJson = getIntent().getStringExtra(FundingDetailActivity.EXTRA_FUNDING);
        mFunding = new Gson().fromJson(fundingJson, PropertyData.class);
        ActivityJoinFundBinding activityBurnTBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_join_fund, mContentLyBase, true);

        burnBch = new JoinFund();
        burnBch.transaction_from = Utils.getNewStyleAddressUsing();
        burnBch.transaction_to = mFunding.issuer;
        burnBch.amount_every_whc = mFunding.tokensperunit;
        activityBurnTBinding.setJoinFund(burnBch);

        findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassWordDialog dialog = new PassWordDialog(JoinCrowdActivityT.this);
                if (TextUtils.isEmpty(burnBch.fee) || TextUtils.isEmpty(burnBch.amount_to_transfer)) {
                    ToastUtils.showLong(R.string.enter_need_first);
                    return;
                }
                Double amountBig = new Double(burnBch.amount_to_transfer);
                final long amountL = (long) (amountBig * 100000000);
                double fee = new Double(burnBch.fee);
                final long feeL = (long) (fee * 100000000);
                if (amountL < 0) {
                    ToastUtils.showLong(R.string.amount_invaliad);
                    return;
                } else if (fee > 0.2) {
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

        Map<String, Object> filedMap = Utils.getMapParams(burnBch);
        Call<BaseResponse<CommonResult>> call = Retrofit.getRetrofit().getService().joinFunding(filedMap);
        call.enqueue(new Callback<BaseResponse<CommonResult>>() {
            @Override
            public void onResponse(Call<BaseResponse<CommonResult>> call, Response<BaseResponse<CommonResult>> response) {
                Callback<JsonElement> callback = new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        if (!Utils.dealPayRes(response)) return;
                        ToastUtils.showLong(R.string.tx_successed);
                        JoinCrowdActivityT.this.finish();
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                };
                if (!Utils.dealCommonNetRes(response)) return;
                TradeAction.pay(storage, response.body().result.unsigned_tx, burnBch.transaction_from, response.body().result.sign_data, callback);
            }

            @Override
            public void onFailure(Call<BaseResponse<CommonResult>> call, Throwable t) {

            }
        });

    }


}

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

import core.core.Storage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import whc.com.whc_wallet.databinding.ActivityDestroyFundBinding;
import whc.com.whc_wallet.databinding.ActivityThawBinding;
import whc.com.whc_wallet.util.Utils;
import whc.com.whc_wallet.view.PassWordDialog;
import wormhole.controllers.TradeAction;
import wormhole.net.Retrofit;
import wormhole.net.request.OpreateFund;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.CommonResult;
import wormhole.net.response.PropertyData;

public class ThawActivity extends BaseActivity {

    private OpreateFund mOpreateFund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fundingJson = getIntent().getStringExtra(FundingDetailActivity.EXTRA_FUNDING);
        PropertyData fund = new Gson().fromJson(fundingJson, PropertyData.class);
        ActivityThawBinding activitySeoBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_thaw, mContentLyBase, true);

        activitySeoBinding.setPropertyData(fund);

        bindViews();
    }


    private void bindViews() {

        findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassWordDialog dialog = new PassWordDialog(ThawActivity.this);
                if (TextUtils.isEmpty(mOpreateFund.amount)
                        || TextUtils.isEmpty(mOpreateFund.fee)) {
                    ToastUtils.showLong(R.string.enter_need_first);
                    return;
                }
                double fee = new Double(mOpreateFund.fee);
                if (fee > 0.2) {
                    ToastUtils.showLong(R.string.fee_invaliad);
                    return;
                }
                dialog.setOnDismissListener(dialog.new PwdDismissListener() {
                    @Override
                    public void onGetStorage(Storage storage) {
                        if (null == storage) return;
                        doTrade(storage);
                    }
                });
                dialog.show();
            }
        });

    }

    private void doTrade(final Storage storage) {
        setTilte(R.string.destory_fund);

        Map filedMap = Utils.getMapParams(mOpreateFund);
        Call<BaseResponse<CommonResult>> call = Retrofit.getRetrofit().getService().burnTokens(filedMap);
        call.enqueue(new Callback<BaseResponse<CommonResult>>() {
            @Override
            public void onResponse(Call<BaseResponse<CommonResult>> call, Response<BaseResponse<CommonResult>> response) {
                Callback<JsonElement> callback = new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        if (!Utils.dealPayRes(response)) return;
                        ToastUtils.showLong(R.string.tx_successed);
                        ThawActivity.this.finish();
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

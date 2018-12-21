package whc.com.whc_wallet;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import whc.com.whc_wallet.databinding.ActivityCreateFixNumFundBinding;
import whc.com.whc_wallet.util.Utils;
import whc.com.whc_wallet.view.PassWordDialog;
import core.core.Storage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wormhole.controllers.TradeAction;
import wormhole.net.Retrofit;
import wormhole.net.request.CreateCrowd;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.CommonResult;

public class CreateFixFundActivity extends BaseActivity {
    public HashMap<String, Object> memoryCache = new HashMap<String, Object>();
    private RelativeLayout mCreateManFundLy;
    private RelativeLayout mCreateFixFundLy;
    private RelativeLayout mCreateCrowdFundLy;
    private CreateCrowd mCreateCrowd = new CreateCrowd();
    private Spinner spinnerP;
    private Spinner spinnerC;
    private Spinner spinnerPrec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreateFixNumFundBinding activityCreateCrowdBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_create_fix_num_fund, mContentLyBase, true);

        mCreateCrowd.transaction_from = Utils.getTestAddress();
        activityCreateCrowdBinding.setCrowd(mCreateCrowd);
        bindViews();
    }


    private void bindViews() {
        setTilte(getString(R.string.fund_fixed_create));
        bindSpinners();

        findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassWordDialog dialog = new PassWordDialog(CreateFixFundActivity.this);
                if (TextUtils.isEmpty(mCreateCrowd.property_name) || TextUtils.isEmpty(mCreateCrowd.property_url)) {
                    ToastUtils.showLong(R.string.enter_need_first);
                    return;
                }
                double fee = mCreateCrowd.fee;
                final long feeL = (long) (fee * 100000000);
                if (fee > 0.2) {
                    ToastUtils.showLong(R.string.fee_invaliad);
                    return;
                }
                if (!mCreateCrowd.property_url.startsWith("http://") && !mCreateCrowd.property_url.startsWith("https://")) {
                    ToastUtils.showShort(R.string.url_format_error);
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



    private void bindSpinners() {
        spinnerPrec = (Spinner) findViewById(R.id.spinnerPrec);
        spinnerP = (Spinner) findViewById(R.id.spinner);
        spinnerC = (Spinner) findViewById(R.id.spinnerChild);
        spinnerP.setOnItemSelectedListener(new SpinnerSelectedListener());
        Call<BaseResponse<Map<String, ArrayList<String>>>> call = Retrofit.getRetrofit().getService().fetchCategory();
        call.enqueue(new Callback<BaseResponse<Map<String, ArrayList<String>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<Map<String, ArrayList<String>>>> call, Response<BaseResponse<Map<String, ArrayList<String>>>> response) {
                memoryCache.put("category", response.body().result);
                List<String> data_list = new ArrayList<String>();
                Set<String> keys = ((Map<String, ArrayList<String>>) memoryCache.get("category")).keySet();
                data_list.addAll(keys);
                //适配器
                ArrayAdapter arr_adapter = new ArrayAdapter<String>(CreateFixFundActivity.this, android.R.layout.simple_spinner_item, data_list);
                //设置样式
                arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //加载适配器
                spinnerP.setAdapter(arr_adapter);

            }

            @Override
            public void onFailure(Call<BaseResponse<Map<String, ArrayList<String>>>> call, Throwable t) {

            }
        });
    }

    private void doTrade(final Storage storage) {
        mCreateCrowd.property_category = (String) spinnerP.getSelectedItem();
        mCreateCrowd.property_subcategory = (String) spinnerC.getSelectedItem();
        mCreateCrowd.precision = Integer.valueOf(spinnerPrec.getSelectedItem().toString());

        Map filedMap = Utils.getMapParams(mCreateCrowd);
        Call<BaseResponse<CommonResult>> call = Retrofit.getRetrofit().getService().createFixedFunding(filedMap);
        call.enqueue(new Callback<BaseResponse<CommonResult>>() {
            @Override
            public void onResponse(Call<BaseResponse<CommonResult>> call, Response<BaseResponse<CommonResult>> response) {
                Callback<JsonElement> callback = new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        if (!Utils.dealPayRes(response)) return;
                        ToastUtils.showLong(R.string.tx_successed);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                };
                if (Utils.dealCommonNetRes(response))
                TradeAction.pay(storage, response.body().result.unsigned_tx, mCreateCrowd.transaction_from, response.body().result.sign_data, callback);

            }

            @Override
            public void onFailure(Call<BaseResponse<CommonResult>> call, Throwable t) {

            }
        });

    }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            String category = (String) spinnerP.getSelectedItem();

            List<String> data_list = new ArrayList<String>();
            ArrayList<String> keys = ((Map<String, ArrayList<String>>) memoryCache.get("category")).get(category);
            data_list.addAll(keys);
            ArrayAdapter arr_adapter = new ArrayAdapter<String>(CreateFixFundActivity.this, android.R.layout.simple_spinner_item, data_list);
            arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerC.setAdapter(arr_adapter);

            String[] precs = {"1", "2", "3", "4", "5", "6", "7", "8"};
            List<String> precList = Arrays.asList(precs);
            ArrayAdapter precAdapter = new ArrayAdapter<String>(CreateFixFundActivity.this, android.R.layout.simple_spinner_item, precList);
            arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPrec.setAdapter(precAdapter);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }




}

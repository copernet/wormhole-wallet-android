package whc.com.whc_wallet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import whc.com.whc_wallet.databinding.ActivityCreateCrowdBinding;
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

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;

public class CreateCrowdActivity extends BaseActivity {
    public HashMap<String, Object> memoryCache = new HashMap<String, Object>();
    private RelativeLayout mCreateManFundLy;
    private RelativeLayout mCreateFixFundLy;
    private RelativeLayout mCreateCrowdFundLy;
    private CreateCrowd mCreateCrowd = new CreateCrowd();
    private Spinner spinnerPrec;
    private Spinner spinnerP;
    private Spinner spinnerC;
    private EditText mDealineEt;
    private EditText mDeadlineTimeEt;
    private Calendar mCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreateCrowdBinding activityCreateCrowdBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_create_crowd, mContentLyBase, true);
        mCreateCrowd.transaction_from = Utils.getTestAddress();
        activityCreateCrowdBinding.setCrowd(mCreateCrowd);
        mCalendar = Calendar.getInstance();
        setTilte(getString(R.string.fund_crowd_create));
        bindViews();
    }

    private void bindViews() {
        bindSpinners();
        bindDataPickers();


        findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassWordDialog dialog = new PassWordDialog(CreateCrowdActivity.this);
                if (TextUtils.isEmpty(mCreateCrowd.property_name)) {
                    ToastUtils.showLong(R.string.enter_need_first);
                    return;
                }
                long amountBig = new Long(mCreateCrowd.total_number);
                final long amountL = (long) (amountBig);
                double fee = mCreateCrowd.fee;
                if (amountL < 0) {
                    ToastUtils.showLong(R.string.amount_invaliad);
                    return;
                } else if (fee > 0.2) {
                    ToastUtils.showLong(R.string.fee_invaliad);
                    return;
                }
                if (!mCreateCrowd.property_url.startsWith("http")) {
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

    private void bindDataPickers() {
        mDealineEt = (EditText) findViewById(R.id.dealineEt);
        mDeadlineTimeEt = (EditText) findViewById(R.id.deadlineTimeEt);
        mDealineEt.setFocusable(false);
        mDeadlineTimeEt.setFocusable(false);


        mDealineEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(new Date());

                Calendar calendarIn = Calendar.getInstance();
                calendarIn.setTime(new Date(System.currentTimeMillis()));
                int startYear = calendarIn.get(Calendar.YEAR);
                int startMonth = calendarIn.get(Calendar.MONTH);
                int startDay = calendarIn.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(CreateCrowdActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, monthOfYear);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        mDealineEt.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, startYear, startMonth, startDay);
                datePicker.getDatePicker().setMinDate(calendarIn.getTimeInMillis());
                datePicker.show();
            }
        });

        mDeadlineTimeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog time = new TimePickerDialog(CreateCrowdActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mCalendar.set(HOUR_OF_DAY, hourOfDay);
                        mCalendar.set(MINUTE, minute);
                        mDeadlineTimeEt.setText(hourOfDay + "-" + minute);
                    }
                }, 18, 25, true);
                time.show();
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
                ArrayAdapter arr_adapter = new ArrayAdapter<String>(CreateCrowdActivity.this, android.R.layout.simple_spinner_item, data_list);
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
        mCreateCrowd.deadline = mCalendar.getTimeInMillis() / 1000;

        Map filedMap = Utils.getMapParams(mCreateCrowd);
        Call<BaseResponse<CommonResult>> call = Retrofit.getRetrofit().getService().createCrowdFunding(filedMap);
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
                if (!Utils.dealCommonNetRes(response)) return;
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
            ArrayAdapter arr_adapter = new ArrayAdapter<String>(CreateCrowdActivity.this, android.R.layout.simple_spinner_item, data_list);
            arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerC.setAdapter(arr_adapter);

            String[] precs = {"1", "2", "3", "4", "5", "6", "7", "8"};
            List<String> precList = Arrays.asList(precs);
            ArrayAdapter precAdapter = new ArrayAdapter<String>(CreateCrowdActivity.this, android.R.layout.simple_spinner_item, precList);
            arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPrec.setAdapter(precAdapter);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


}

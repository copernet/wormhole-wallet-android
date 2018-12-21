package whc.com.whc_wallet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import whc.com.whc_wallet.util.Utils;
import whc.com.whc_wallet.view.CustomerDialog;
import whc.com.whc_wallet.view.UpdateVersionDialog;
import wormhole.net.Retrofit;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.CheckUpdate;

public class AboutUsActivity extends BaseActivity {
    private TextView mVerionTipTv;
    private TextView mCurrentVerionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        mVerionTipTv = findViewById(R.id.verionTipTv);
        mCurrentVerionTv = findViewById(R.id.currentVerionTv);
        mCurrentVerionTv.setText(AppUtils.getAppVersionName());
        setTilte(getString(R.string.about_us));
        findViewById(R.id.checkUpdateLy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchNet();
            }
        });
        fetchNet();
    }

    protected void showUpdataDialog(final CheckUpdate result) {

        UpdateVersionDialog dialog = new UpdateVersionDialog(AboutUsActivity.this, getString(R.string.update_version), result);
        dialog.show();

    }

    public void fetchNet() {

        mVerionTipTv.setText(R.string.update_checking);
        Call<BaseResponse<CheckUpdate>> call =  Retrofit.getRetrofit().getService().checkUpdate();
        call.enqueue(new Callback<BaseResponse<CheckUpdate>>() {
            @Override
            public void onResponse(Call<BaseResponse<CheckUpdate>> call, Response<BaseResponse<CheckUpdate>> response) {
                if (!Utils.dealCommonNetRes(response)) return;
                int localVersionCode = AppUtils.getAppVersionCode();
                if (response.body().result.versionCode > localVersionCode) {
                    showUpdataDialog(response.body().result);
                    mVerionTipTv.setText(getString(R.string.has_new_version) + response.body().result.version);
                } else {
                    mVerionTipTv.setText(getString(R.string.is_newest_version_now));
                }

            }

            @Override
            public void onFailure(Call<BaseResponse<CheckUpdate>> call, Throwable t) {

            }
        });

    }




}

package whc.com.whc_wallet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;
import wormhole.net.response.BasePageResponse;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.HistrotyRes;

public class BaseActivity extends AutoLayoutActivity implements EasyPermissions.PermissionCallbacks{
    private ImageView mTitleLeftIv;
    private TextView mTitleBarTv;
    private ImageView mTitleBarRightIv;

    private RelativeLayout mNoMsgLy;
    private RelativeLayout mNetErrorLy;
    private Button mRetryNetBtn;
    protected LinearLayout mContentLyBase;
    protected EditText addrDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        //HACK(qiaochuanbei) 为了方便测试
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        mNoMsgLy = (RelativeLayout) findViewById(R.id.noMsgLy);
        mNetErrorLy = (RelativeLayout) findViewById(R.id.netErrorLy);
        mRetryNetBtn = (Button) findViewById(R.id.retryNetBtn);
        mContentLyBase = (LinearLayout) findViewById(R.id.contentLyBase);

        mRetryNetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchNet();
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initActionBar();
        showContentView();
        boolean b = EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
        if (!b) {
            EasyPermissions.requestPermissions(this, "Permission Need", 0, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
        }
    }

    public void fetchNet() {
    }

    @Override
    public void setContentView(View view) {
        ViewGroup vg = findViewById(R.id.contentLyBase);
        vg.addView(view);
        showContentView();
    }

    @Override
    public void setContentView(int layoutResID) {
        ViewGroup vg = findViewById(R.id.contentLyBase);
        View contentView =  LayoutInflater.from(this).inflate(layoutResID, null);
        vg.addView(contentView);
        showContentView();
    }

    public void showNoMsgView() {
        mNoMsgLy.setVisibility(View.VISIBLE);
        mNetErrorLy.setVisibility(View.GONE);
        mContentLyBase.setVisibility(View.GONE);
    }

    public void showNetErrorView() {
        mNetErrorLy.setVisibility(View.VISIBLE);
        mNoMsgLy.setVisibility(View.GONE);
        mContentLyBase.setVisibility(View.GONE);
    }

    public void showContentView() {
        mContentLyBase.setVisibility(View.VISIBLE);
        mNoMsgLy.setVisibility(View.GONE);
        mNetErrorLy.setVisibility(View.GONE);
    }

    public <T> boolean dealCommonListRes(Response<BaseResponse<BasePageResponse<T>>> response, List localList) {
        if (null != localList && localList.size() > 0) {
            showContentView();
            return true;
        } else if (null == response.body().result.list || response.body().result.list.size() + localList.size() <= 0) {
            showNoMsgView();
            return false;
        } else {
            showContentView();
            return true;
        }
    }


    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View view = getLayoutInflater().inflate(R.layout.actionbar_title,null);
        mTitleLeftIv = (ImageView) view.findViewById(R.id.titleLeftIv);
        mTitleBarTv = (TextView) view.findViewById(R.id.titleBarTv);
        mTitleBarRightIv = (ImageView) view.findViewById(R.id.titleBarRightIv);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(view, layoutParams);
        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        mTitleLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finish();
            }
        });
    }

    protected void setTilte(String tilte) {
        mTitleBarTv.setText(tilte);
    }

    protected void setTilte(int id) {
        String title = getString(id);
        setTilte(title);
    }

    protected void setRightIcon(int drawableResId) {
        if (drawableResId < 0) {
            mTitleBarRightIv.setVisibility(View.GONE);
        } else {
            mTitleBarRightIv.setBackgroundResource(drawableResId);
        }
    }

    protected void setRightOnclickListener(View.OnClickListener onclickListner) {
        mTitleBarRightIv.setOnClickListener(onclickListner);
    }

    protected void setLeftOnclickListener(View.OnClickListener onclickListner) {
        mTitleLeftIv.setOnClickListener(onclickListner);
    }

    protected void setLeftIcon(int drawableResId) {
        if (drawableResId < 0) {
            mTitleLeftIv.setVisibility(View.GONE);
        } else {
            mTitleLeftIv.setBackgroundResource(drawableResId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    addrDetail.setText(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(BaseActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * 重写onRequestPermissionsResult，用于接受请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 请求权限成功。
     * 可以弹窗显示结果，也可执行具体需要的逻辑操作
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }
    /**
     * 请求权限失败
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        System.exit(0);
//        finish();
        /**
         * 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
         * 这时候，需要跳转到设置界面去，让用户手动开启。
         */
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

}

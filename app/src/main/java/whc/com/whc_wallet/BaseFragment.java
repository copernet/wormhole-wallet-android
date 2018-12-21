package whc.com.whc_wallet;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
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

import com.goyourfly.tabviewpager.BaseTabViewPagerFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;
import wormhole.net.response.BasePageResponse;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.HistrotyRes;

public class BaseFragment extends BaseTabViewPagerFragment {
    private ImageView mTitleLeftIv;
    private TextView mTitleBarTv;
    private ImageView mTitleBarRightIv;

    private RelativeLayout mNoMsgLy;
    private RelativeLayout mNetErrorLy;
    private Button mRetryNetBtn;
    protected LinearLayout mContentLyBase;
    protected EditText addrDetail;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNoMsgLy = (RelativeLayout) getView().findViewById(R.id.noMsgLy);
        mNetErrorLy = (RelativeLayout) getView().findViewById(R.id.netErrorLy);
        mRetryNetBtn = (Button) getView().findViewById(R.id.retryNetBtn);
        mContentLyBase = (LinearLayout) getView().findViewById(R.id.contentLyBase);

        mRetryNetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchNet();
            }
        });

        showContentView();
    }

    public void fetchNet() {
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
        } else if (null == response || null == response.body() || null == response.body().result.list || response.body().result.list.size() + localList.size() <= 0) {
            showNoMsgView();
            return false;
        } else {
            showContentView();
            return true;
        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public RecyclerView getRecyclerView() {
        return null;
    }
}

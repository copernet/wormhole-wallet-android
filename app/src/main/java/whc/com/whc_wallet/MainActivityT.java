package whc.com.whc_wallet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.utils.AutoUtils;

import core.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import whc.com.whc_wallet.adapter.TabFragmentAdapter;
import whc.com.whc_wallet.util.Utils;
import whc.com.whc_wallet.view.TabContainerView;
import whc.com.whc_wallet.view.UpdateVersionDialog;
import wormhole.net.Retrofit;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.CheckUpdate;
import wormhole.net.response.GetFee;

public class MainActivityT extends AutoLayoutActivity implements ViewPager.OnPageChangeListener{

    public static MainActivityT sMainActivity;


    /**
     * tab图标集合
     */
    private final int ICONS_RES[][] = {
            {
                    R.drawable.tab_home_nor,
                    R.drawable.tab_home_sel
            },
            {
                    R.drawable.tab_browse_nor,
                    R.drawable.tab_browse_sel
            },

            {
                    R.drawable.tab_assert_nor,
                    R.drawable.tab_assert_sel
            },

            {
                    R.drawable.tab_my_nor,
                    R.drawable.tab_my_sel
            }
    };

    /** tab 颜色值*/
    private final int[] TAB_COLORS = new int []{
            R.color.main_bottom_tab_textcolor_normal,
            R.color.main_bottom_tab_textcolor_selected};

    private Fragment[] fragments = {
            new HomeFragment(),
            new OverviewParentFragment(),
            new FundFragment(),
            new MineFragment()
    };
    private TabContainerView mTabLayout;
    private long firstTime = 0;

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtils.showShort(R.string.press_more_exit);
            firstTime = secondTime;
        } else {
            System.exit(0);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.getConfig().setLogSwitch(Constants.TEST_NET);
//        LogUtils.getConfig().setLogSwitch(true);
        Utils.initLocaleLanguage(this);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.main_activity_t);
        ZXingLibrary.initDisplayOpinion(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sMainActivity = this;
        initViews();
        getDatas();

    }

    private void getDatas() {
        fetchFee();
        fetchUpdateInfo();
    }

    public void fetchUpdateInfo() {

        Call<BaseResponse<CheckUpdate>> call =  Retrofit.getRetrofit().getService().checkUpdate();
        call.enqueue(new Callback<BaseResponse<CheckUpdate>>() {
            @Override
            public void onResponse(Call<BaseResponse<CheckUpdate>> call, Response<BaseResponse<CheckUpdate>> response) {
                if (!Utils.dealCommonNetRes(response)) return;
                int localVersionCode = AppUtils.getAppVersionCode();
                if (response.body().result.versionCode > localVersionCode) {
                    showUpdataDialog(response.body().result);
                } else {
                }

            }

            @Override
            public void onFailure(Call<BaseResponse<CheckUpdate>> call, Throwable t) {

            }
        });

    }

    protected void showUpdataDialog(final CheckUpdate result) {
        UpdateVersionDialog dialog = new UpdateVersionDialog(MainActivityT.this, getString(R.string.update_version), result);
        dialog.show();

    }

    private void fetchFee() {

        Call<BaseResponse<GetFee>> call =  Retrofit.getRetrofit().getService().getFee();
        call.enqueue(new Callback<BaseResponse<GetFee>>() {
            @Override
            public void onResponse(Call<BaseResponse<GetFee>> call, Response<BaseResponse<GetFee>> response) {
                if (Utils.dealCommonNetRes(response))
                Utils.saveFee(response.body().result);
            }

            @Override
            public void onFailure(Call<BaseResponse<GetFee>> call, Throwable t) {

            }
        });
        mTabLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.fetchNotices();
            }
        }, 2000);
    }


    private void initViews() {
        TabFragmentAdapter mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments);
        ViewPager mPager = (ViewPager) findViewById(R.id.tab_pager);
        //设置当前可见Item左右可见page数，次范围内不会被销毁
        mPager.setOffscreenPageLimit(3);
        mPager.setAdapter(mAdapter);

        mTabLayout = (TabContainerView) findViewById(R.id.ll_tab_container);
        mTabLayout.setOnPageChangeListener(this);

        mTabLayout.initContainer(getResources().getStringArray(R.array.tab_main_title), ICONS_RES, TAB_COLORS, true);

        int width = AutoUtils.getPercentWidthSize(66);
        int height = AutoUtils.getPercentWidthSize(66);
        mTabLayout.setContainerLayout(R.layout.tab_container_view, R.id.iv_tab_icon, R.id.tv_tab_text, width, height);

        mTabLayout.setViewPager(mPager);

        mPager.setCurrentItem(getIntent().getIntExtra("tab", 0));

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int index = 0, len = fragments.length; index < len; index ++) {
            fragments[index].onHiddenChanged(index != position);
        }
    }

    public void showNoticeRedIcon() {
        mTabLayout.showRedIcon(3);
        ((MineFragment)fragments[3]).showNoticeRedIcon();
    }

    public void hideNoticeRedIcon() {
        mTabLayout.hideRedIcon(3);
        ((MineFragment)fragments[3]).hideNoticeRedIcon();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
}

package whc.com.whc_wallet;

import java.util.ArrayList;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.zhy.autolayout.utils.AutoUtils;

import shanyao.tabpagerindictor.TabPageIndicator;

public class NoticeCenterActivity extends FragmentActivity {

    private ViewPager myvirwpager;
    private ArrayList<Fragment> list;
    private BasePagerAdapter adapter;
    FragmentManager fm = getSupportFragmentManager();
    TxNoticeFragment txFragment;
    SysNoticeFragment sysFragment;

    private TabPageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_center);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        indicator = (TabPageIndicator) findViewById(R.id.indicator);
        myvirwpager = (ViewPager) findViewById(R.id.myviewpager);

        list = new ArrayList<Fragment>();
        txFragment = new TxNoticeFragment();
        list.add(txFragment);

        sysFragment = new SysNoticeFragment();
        list.add(sysFragment);
        adapter = new BasePagerAdapter(fm);
        myvirwpager.setAdapter(adapter);

        myvirwpager.setAdapter(adapter);
        indicator.setViewPager(myvirwpager);

        findViewById(R.id.titleLeftIv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setTabPagerIndicator();

    }

    class BasePagerAdapter extends FragmentPagerAdapter {
        String[] titles;

        public BasePagerAdapter(FragmentManager fm) {
            super(fm);
            this.titles = new String[]{"tx notices", "system notices"};
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


    private void setTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_SAME);
        indicator.setDividerColor(Color.parseColor("#00000000"));
        indicator.setDividerPadding(1);
        indicator.setIndicatorColor(Color.parseColor("#0C66FF"));
        indicator.setTextColorSelected(Color.parseColor("#53627C"));
        indicator.setTextColor(Color.parseColor("#A6AEBC"));
        indicator.setTextSize(AutoUtils.getPercentWidthSize(48));
    }

}

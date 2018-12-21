package whc.com.whc_wallet;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

import whc.com.whc_wallet.util.Utils;

public class ChooseLanguageActivity extends BaseActivity {
    private RadioGroup mLanguageRadioG;
    private RadioButton mChineseBtn;
    private RadioButton mEnglishBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTilte("Language");
        setContentView(R.layout.activity_choose_language);
        mLanguageRadioG = (RadioGroup) findViewById(R.id.languageRadioG);
        mChineseBtn = (RadioButton) findViewById(R.id.chineseBtn);
        mEnglishBtn = (RadioButton) findViewById(R.id.englishBtn);

        mLanguageRadioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.chineseBtn:
                        Utils.setLanguage("zh");
                        break;
                    case R.id.englishBtn:
                        Utils.setLanguage("en");
                        break;

                }
//                Intent it = new Intent(ChooseLanguageActivity.this, MainActivityT.class);
//                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(it);
//                finish();
            }
        });
    }


    /**
     * 切换英文
     */
    public void en(View v){
        Resources resources = getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = Locale.ENGLISH; // 英文
        resources.updateConfiguration(config, dm);
        Intent it = new Intent(this, MainActivityT.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(it);
        finish();//如果不重启当前界面，是不会立马修改的
    }
    /**
     * 切换中文
     */
    public void cn(View v){
        Resources resources = getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = Locale.CHINA; // 简体中文
        resources.updateConfiguration(config, dm);
        finish();////如果不重启当前界面，是不会立马修改的
        Intent it = new Intent(this, MainActivityT.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(it);
    }



}

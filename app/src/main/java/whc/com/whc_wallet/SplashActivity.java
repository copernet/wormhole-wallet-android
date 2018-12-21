package whc.com.whc_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;

import com.blankj.utilcode.util.LogUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import core.util.Constants;
import whc.com.whc_wallet.util.AppSignCheck;
import whc.com.whc_wallet.util.Utils;
import core.util.Files;

public class SplashActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.getConfig().setLogSwitch(Constants.TEST_NET);
        Utils.initLocaleLanguage(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        findViewById(R.id.splashIv).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = null;
                if (TextUtils.isEmpty(Utils.getCurrentWalletName(SplashActivity.this)) ||
                        !Files.isExist(Utils.getSecureStoragePathCurrent())) {
                    it = new Intent(SplashActivity.this, CreateOrImportActivity.class);
                } else {
                    it = new Intent(SplashActivity.this, MainActivityT.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                }
                startActivity(it);
                finish();
            }
        }, 1000);
    }


}

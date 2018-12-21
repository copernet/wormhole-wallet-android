package whc.com.whc_wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import core.core.Storage;
import whc.com.whc_wallet.view.PassWordDialog;

public class SeedWordLookActivity extends BaseActivity {
    public static final String EXTRA_SEED = "seed";

    private ImageView mNameIv;
    private EditText mWalletNameEt;
    private ImageView mLockIv;
    private EditText mPassWordEt;
    private EditText mRepeatPwdEt;
    private String mSeedWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_seed);
        showPwdDialog();
    }

    private void bindViews() {
        setTilte(R.string.look_seed);
        ((TextView)findViewById(R.id.showSeedTv)).setText(mSeedWord);

        findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void showPwdDialog() {

        PassWordDialog dialog = new PassWordDialog(SeedWordLookActivity.this);
        dialog.setOnDismissListener(dialog.new PwdDismissListener() {
            @Override
            public void onGetStorage(Storage storage) {
                if (null == storage){
                    finish();
                    return;
                }
                mSeedWord = storage.get("seed", "");
                bindViews();
            }
        });
        dialog.show();
    }


}

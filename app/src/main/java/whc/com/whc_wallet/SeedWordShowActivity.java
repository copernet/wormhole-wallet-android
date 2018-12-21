package whc.com.whc_wallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import whc.com.whc_wallet.util.MessageEvent;
import whc.com.whc_wallet.view.NoticeDialog;

public class SeedWordShowActivity extends BaseActivity {
    public static final String EXTRA_SEED = "seed";

    private ImageView mNameIv;
    private EditText mWalletNameEt;
    private ImageView mLockIv;
    private EditText mPassWordEt;
    private EditText mRepeatPwdEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_seed);
        bindViews();
        getDatas();
    }

    private void getDatas() {
        EventBus.getDefault().register(this);
    }

    @Subscribe()
    public void closePage(MessageEvent event) {
        if (0 == event.getMsgType()) {
            this.finish();
        }
    }

    private void bindViews() {
        setTilte(R.string.create_wallet_title);
        final String seedWord = getIntent().getStringExtra(EXTRA_SEED);
        ((TextView)findViewById(R.id.showSeedTv)).setText(seedWord);

        findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(SeedWordShowActivity.this, SeedWordEnterActivity.class);
                it.putExtra(SeedWordShowActivity.EXTRA_SEED, seedWord);
                startActivity(it);
            }
        });
        showWarning();
    }

    private void showNotice() {
        String noticeTx1 = getString(R.string.screen_shot_tip);
        String noticeTx2 = getString(R.string.write_word_tip);
        String noticeTx3 = getString(R.string.save_seed_tip);
        String title = "Matters needing attention";
        NoticeDialog dialog = new NoticeDialog(SeedWordShowActivity.this, R.drawable.create_icon_security, title, new String[]{noticeTx1, noticeTx2, noticeTx3});
        dialog.show();
    }

    private void showWarning() {
        String noticeTx1 = "If you lost your password, you will lost you money!";
        String noticeTx2 = "If app uninstalled or lost your phone, you will lost you money!";
        String title = getString(R.string.what_happen_no_seed);
        NoticeDialog dialog = new NoticeDialog(SeedWordShowActivity.this, R.drawable.create_icon_word, title, new String[]{noticeTx1, noticeTx2});
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                showNotice();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}

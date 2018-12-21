package whc.com.whc_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import whc.com.whc_wallet.util.MessageEvent;
import whc.com.whc_wallet.view.CustomerDialog;
import whc.com.whc_wallet.view.ShellTokenizer;
import core.core.Seed;

public class SeedWordEnterActivity extends BaseActivity {

    private ImageView mNameIv;
    private EditText mWalletNameEt;
    private ImageView mLockIv;
    private EditText mPassWordEt;
    private EditText mRepeatPwdEt;
    private MultiAutoCompleteTextView seedTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_seed);
        bindViews();
    }

    private void bindViews() {
        setTilte(R.string.input_seed);
        final String seedWord = getIntent().getStringExtra(SeedWordShowActivity.EXTRA_SEED);

        List<String> words = new Seed().getWordList();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                words);

        seedTv = (MultiAutoCompleteTextView) findViewById(R.id.showSeedTv);
        seedTv.setTokenizer(new ShellTokenizer(this, ' ', true));
        seedTv.setAdapter(adapter);
        setEditTextInhibitInputSpace(seedTv);


        findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seedEntered = seedTv.getText().toString().trim();
                if (seedWord.equals(seedEntered)) {
                    EventBus.getDefault().post(new MessageEvent(0));
                    Intent it = new Intent(SeedWordEnterActivity.this, MainActivityT.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(it);
                    finish();
                } else {
                    String title = getString(R.string.enter_correctly_seed);
                    String tipText = getString(R.string.seed_error_tip);
                    CustomerDialog customerDialog = new CustomerDialog(SeedWordEnterActivity.this, title, tipText);
                    customerDialog.show();
                }
            }
        });
    }

    /**
     * 禁止EditText输入空格
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText){
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals(" ") || source.equals("\n")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }


}

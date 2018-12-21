package whc.com.whc_wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import whc.com.whc_wallet.util.Utils;
import whc.com.whc_wallet.view.CustomerDialog;
import whc.com.whc_wallet.view.PassWordDialog;
import core.core.Storage;


public class ChangePwdActivity extends BaseActivity {
    private EditText mPassWordEt;
    private EditText mRepeatPwdEt;
    private String password;
    private Storage mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        bindViews();
        showPwdDialog();
    }

    private void bindViews() {
        setTilte(R.string.change_pwd);
        mPassWordEt = (EditText) findViewById(R.id.passWordEt);
        mRepeatPwdEt = (EditText) findViewById(R.id.repeatPwdEt);
        findViewById(R.id.confirmChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = "" + mPassWordEt.getText();
                if (password.length() < 8 || !Utils.isValidPassword(password.toString())) {
                    LogUtils.d("Not Valid");
                    CustomerDialog dialog = new CustomerDialog(ChangePwdActivity.this, getString(R.string.please_enter_correct_pwd), getString(R.string.password_hint));
                    dialog.show();
                    return;
                } else {
                    LogUtils.d("Valid");
                }
                String repeatPwd = mRepeatPwdEt.getText().toString();
                if (!password.equals(repeatPwd)) {
                    CustomerDialog dialog = new CustomerDialog(ChangePwdActivity.this, getString(R.string.pwd_not_match), "");
                    dialog.show();
                    return;
                }
                mStorage.changePwd(password);
                finish();
                ToastUtils.showShort(R.string.change_success);
            }
        });
    }

    private void showPwdDialog() {

        PassWordDialog dialog = new PassWordDialog(ChangePwdActivity.this);
        dialog.setOnDismissListener(dialog.new PwdDismissListener() {
            @Override
            public void onGetStorage(Storage storage) {
                if (null == storage) finish();
                mStorage = storage;
            }
        });
        dialog.show();
    }


}

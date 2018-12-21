package whc.com.whc_wallet.view;

/**
 * Created by chuanbei.qiao on 2018/11/2.
 */
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.bouncycastle.crypto.InvalidCipherTextException;

import java.io.IOException;

import whc.com.whc_wallet.R;
import whc.com.whc_wallet.util.Utils;
import core.core.Storage;

public class PassWordDialog extends Dialog {

    private Context context;
    private EditText mPwdEt;
    private TextView mPwdErrorTv;
    private Button mConfirmBtn;
    private Storage mStorage;

    public PassWordDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_input_password, null);
        setContentView(view);
        mPwdEt = (EditText) findViewById(R.id.pwdEt);
        mPwdErrorTv = (TextView) findViewById(R.id.pwdErrorTv);
        mConfirmBtn = (Button) findViewById(R.id.confirmBtn);

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwdInput = "" + mPwdEt.getText();
                try {
                    mStorage = new Storage(Utils.getSecureStoragePathCurrent(), pwdInput);
                    dismiss();
                } catch (InvalidCipherTextException e) {
                    mPwdErrorTv.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void setOnDismissListener(@Nullable PwdDismissListener listener) {
        super.setOnDismissListener(listener);
    }

    public abstract class PwdDismissListener implements OnDismissListener {

        @Override
        public void onDismiss(DialogInterface dialog) {
            onGetStorage(mStorage);
        }

        public abstract void onGetStorage(Storage storage);
    }

    ;

}

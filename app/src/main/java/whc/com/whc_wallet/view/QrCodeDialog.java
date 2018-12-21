package whc.com.whc_wallet.view;

/**
 * Created by chuanbei.qiao on 2018/11/2.
 */
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import whc.com.whc_wallet.R;

import static android.content.Context.CLIPBOARD_SERVICE;

public class QrCodeDialog extends Dialog {

    private Context context;
    private String name;
    private String addr;
    private ImageView mQrIv;
    private TextView mNameTv;
    private TextView mAddrTv;
    private View mCopyBtn;

    public QrCodeDialog(Context context, String name, String addr) {
        super(context);
        this.context = context;
        this.name = name;
        this.addr = addr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_qr_code, null);
        setContentView(view);

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        mQrIv = (ImageView) findViewById(R.id.qrIv);
        mNameTv = (TextView) findViewById(R.id.nameTv);
        mAddrTv = (TextView) findViewById(R.id.addrTv);
        mCopyBtn = findViewById(R.id.copyBtn);

        mAddrTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ((TextView)v).getText().toString();
                ClipData myClip = ClipData.newPlainText("text", text);
                ClipboardManager myClipboard =
                        (ClipboardManager) ((TextView) v).getContext().getSystemService(CLIPBOARD_SERVICE);
                myClipboard.setPrimaryClip(myClip);
                ToastUtils.showLong(R.string.copy_already);
            }
        });

        mCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mAddrTv.getText().toString();
                ClipData myClip = ClipData.newPlainText("text", text);
                ClipboardManager myClipboard =
                        (ClipboardManager) v.getContext().getSystemService(CLIPBOARD_SERVICE);
                myClipboard.setPrimaryClip(myClip);
                ToastUtils.showLong(R.string.copy_already);
            }
        });

        mNameTv.setText(name);
        mAddrTv.setText(addr);

        Bitmap mBitmap = CodeUtils.createImage(addr, 400, 400, null);
        mQrIv.setImageBitmap(mBitmap);

    }

}

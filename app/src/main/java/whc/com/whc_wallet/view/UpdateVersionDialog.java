package whc.com.whc_wallet.view;

/**
 * Created by chuanbei.qiao on 2018/11/2.
 */
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import whc.com.whc_wallet.R;
import wormhole.net.response.CheckUpdate;

public class UpdateVersionDialog extends Dialog {

    private Context context;
    private String title;
    private CheckUpdate detailText;
    private View cancleBtn;

    public interface ClickListenerInterface {

        public void doConfirm();

        public void doCancel();
    }

    public UpdateVersionDialog(Context context, String title, CheckUpdate detailText) {
        super(context);
        this.context = context;
        this.title = title;
        this.detailText = detailText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update_version, null);
        setContentView(view);

        TextView tvTitle = (TextView) view.findViewById(R.id.titleTv);
        TextView tvConfirm = (TextView) view.findViewById(R.id.confirmBtn);
        TextView detailTv = (TextView) view.findViewById(R.id.detailTv);
        cancleBtn = view.findViewById(R.id.cancleBtn);

        tvTitle.setText(title);
        detailTv.setText(detailText.description.replace("\\n","\n"));
        tvConfirm.setOnClickListener(new ClickListener());
        cancleBtn.setOnClickListener(new ClickListener());

//        Window dialogWindow = getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
//        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
//        dialogWindow.setAttributes(lp);
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.confirmBtn:
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(detailText.download));
                    intent.setAction(Intent.ACTION_VIEW);
                    getContext().startActivity(intent);
                    break;
                case R.id.cancleBtn:
                    UpdateVersionDialog.this.dismiss();
                    break;
            }
        }

    }

    ;

}

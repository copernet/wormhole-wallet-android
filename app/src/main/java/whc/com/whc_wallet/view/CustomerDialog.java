package whc.com.whc_wallet.view;

/**
 * Created by chuanbei.qiao on 2018/11/2.
 */
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import whc.com.whc_wallet.R;

public class CustomerDialog extends Dialog {

    private Context context;
    private String title;
    private String detailText;

    public interface ClickListenerInterface {

        public void doConfirm();

        public void doCancel();
    }

    public CustomerDialog(Context context, String title, String detailText) {
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
        View view = inflater.inflate(R.layout.dialog_seed_error, null);
        setContentView(view);

        TextView tvTitle = (TextView) view.findViewById(R.id.titleTv);
        TextView tvConfirm = (TextView) view.findViewById(R.id.confirmBtn);
        TextView detailTv = (TextView) view.findViewById(R.id.detailTv);

        tvTitle.setText(title);
        detailTv.setText(detailText);
        tvConfirm.setOnClickListener(new ClickListener());

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
                    CustomerDialog.this.dismiss();
                    break;
            }
        }

    }

    ;

}

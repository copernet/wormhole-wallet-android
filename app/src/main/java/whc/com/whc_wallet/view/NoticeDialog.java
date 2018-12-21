package whc.com.whc_wallet.view;

/**
 * Created by chuanbei.qiao on 2018/11/2.
 */
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import whc.com.whc_wallet.R;

public class NoticeDialog extends Dialog {

    private Context context;
    private String title;
    private int mIconId;
    private String[] detailTexts;

    private ImageView mNoticeIv;
    private TextView mTitleTv;
    private LinearLayout mNoticeTextLy;

    public NoticeDialog(Context context,int iconId, String title, String[] detailText) {
        super(context);
        mIconId = iconId;
        this.context = context;
        this.title = title;
        this.detailTexts = detailText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_notice, null);
        setContentView(view);

        TextView tvTitle = (TextView) view.findViewById(R.id.titleTv);
        TextView tvConfirm = (TextView) view.findViewById(R.id.confirmBtn);

        mNoticeIv = (ImageView) findViewById(R.id.noticeIv);
        mTitleTv = (TextView) findViewById(R.id.titleTv);
        mNoticeTextLy = (LinearLayout) findViewById(R.id.noticeTextLy);

        mNoticeIv.setBackgroundResource(mIconId);
        for (int i = 0; i < detailTexts.length; i++) {
            View noticeViewLy = LayoutInflater.from(getContext()).inflate(R.layout.textview_notice, null);
            TextView noticeView = noticeViewLy.findViewById(R.id.noticeTv);
            noticeView.setText(detailTexts[i]);
            mNoticeTextLy.addView(noticeViewLy);
        }

        tvTitle.setText(title);
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
                    NoticeDialog.this.dismiss();
                    break;
            }
        }

    }

    ;

}

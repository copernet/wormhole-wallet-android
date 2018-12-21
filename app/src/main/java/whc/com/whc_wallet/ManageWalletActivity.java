package whc.com.whc_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Set;

import whc.com.whc_wallet.util.Utils;

public class ManageWalletActivity extends BaseActivity {

    private ViewGroup mCardLy;
    private Button createBtn;
    private Button importBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_wallet);
        bindViews();
        refreshViews();
    }

    private void bindViews() {
        setTilte(getString(R.string.my_wallet));
        mCardLy = findViewById(R.id.cardLy);
        createBtn = findViewById(R.id.createBtn);
        importBtn = findViewById(R.id.importBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ManageWalletActivity.this, PassWordActivityT.class);
                startActivity(it);
            }
        });
        importBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ManageWalletActivity.this, ImportWalletActivity.class);
                startActivity(it);
            }
        });
    }

    private void refreshViews() {
        Set<String> detailTexts = Utils.getWalletNameList(this);
        for (final String text : detailTexts) {
            View noticeViewLy = LayoutInflater.from(ManageWalletActivity.this).inflate(R.layout.item_manage_wallet, null);
            TextView nameTv = noticeViewLy.findViewById(R.id.nameTv);
            noticeViewLy.setTag(text);
            nameTv.setText(text);
            TextView addrTv = noticeViewLy.findViewById(R.id.addrTv);
            String addr = Utils.getNewStyleAddrByWalletName(text);
            addrTv.setText(addr);
            noticeViewLy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.setCurrentWalletName(ManageWalletActivity.this, "" + v.getTag());
                    finish();
                }
            });
            mCardLy.addView(noticeViewLy);
        }
    }




}

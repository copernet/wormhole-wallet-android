package whc.com.whc_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class CreateFundActivity extends BaseActivity {
    private RelativeLayout mCreateManFundLy;
    private RelativeLayout mCreateFixFundLy;
    private RelativeLayout mCreateCrowdFundLy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_fund);
        bindViews();
    }

    private void bindViews() {
        setTilte(getString(R.string.fund_create));
        mCreateManFundLy = (RelativeLayout) findViewById(R.id.createManFundLy);
        mCreateFixFundLy = (RelativeLayout) findViewById(R.id.createFixFundLy);
        mCreateCrowdFundLy = (RelativeLayout) findViewById(R.id.createCrowdFundLy);
        mCreateManFundLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CreateFundActivity.this, CreateManagableActivity.class);
                startActivity(it);
            }
        });
        mCreateFixFundLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CreateFundActivity.this, CreateFixFundActivity.class);
                startActivity(it);
            }
        });
        mCreateCrowdFundLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CreateFundActivity.this, CreateCrowdActivity.class);
                startActivity(it);
            }
        });

    }


}

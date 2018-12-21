package whc.com.whc_wallet.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.math.BigDecimal;

import whc.com.whc_wallet.R;
import whc.com.whc_wallet.util.Utils;
import wormhole.net.response.GetFee;

/**
 * Created by chuanbei.qiao on 2018/11/5.
 */

public class MinerFeeView extends RelativeLayout {

    private RadioButton mBtn_0;
    private RadioButton mBtn_1;
    private RadioButton mBtn_2;
    private RadioGroup feeRadioGroup;
    private GetFee fee;

    public MinerFeeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.radio_btn_miner_fee, this);

        mBtn_0 = (RadioButton) findViewById(R.id.btn_0);
        mBtn_1 = (RadioButton) findViewById(R.id.btn_1);
        mBtn_2 = (RadioButton) findViewById(R.id.btn_2);
        feeRadioGroup = (RadioGroup) findViewById(R.id.feeRadioGroup);
        fee = Utils.getFee();

        feeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                BigDecimal bd = new BigDecimal(fee.Slow);
                bd = bd.setScale(8, BigDecimal.ROUND_HALF_UP);
                String feeStr;
                switch (checkedId) {
                    case R.id.btn_0:
                        bd = new BigDecimal(fee.Slow);
                        bd = bd.setScale(8, BigDecimal.ROUND_HALF_UP);
                        feeStr = bd.toEngineeringString();
                        ((EditText) ((Activity) getContext()).findViewById(R.id.feeEt)).setText(feeStr);
                        break;
                    case R.id.btn_1:
                        bd = new BigDecimal(fee.Normal);
                        bd = bd.setScale(8, BigDecimal.ROUND_HALF_UP);
                        feeStr = bd.toEngineeringString();
                        ((EditText) ((Activity) getContext()).findViewById(R.id.feeEt)).setText(feeStr);
                        break;
                    case R.id.btn_2:
                        bd = new BigDecimal(fee.Fast);
                        bd = bd.setScale(8, BigDecimal.ROUND_HALF_UP);
                        feeStr = bd.toEngineeringString();
                        ((EditText) ((Activity) getContext()).findViewById(R.id.feeEt)).setText(feeStr);
                        break;

                }
            }
        });

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        feeRadioGroup.check(R.id.btn_1);
    }
}

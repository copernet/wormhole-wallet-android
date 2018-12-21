package whc.com.whc_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import core.core.ApplicationContext;
import whc.com.whc_wallet.util.Utils;
import whc.com.whc_wallet.view.PassWordDialog;
import core.core.AddressUtil;
import core.core.Network;
import core.core.Storage;

public class SendActivity extends BaseActivity {
    private TextView feeEt;
    private Network network;
    private Button confirmBtn;
    private TextView balanceDetail;
    private EditText moneyAmontEt;
    private TextView mOutAddrTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_t);
        bindViews();
    }

    private void bindViews() {
        setTilte(R.string.send);
        addrDetail = findViewById(R.id.addrDetail);
        mOutAddrTv = findViewById(R.id.outAddrTv);
        balanceDetail = findViewById(R.id.balanceDetail);
        network = HomeFragment.network;
        feeEt = findViewById(R.id.feeEt);
        confirmBtn = findViewById(R.id.confirmBtn);
        moneyAmontEt = findViewById(R.id.money);

        mOutAddrTv.setText(Utils.getNewStyleAddressUsing());
        setRightIcon(R.drawable.main_icon_qr);
        setRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassWordDialog dialog = new PassWordDialog(SendActivity.this);
                final String address = addrDetail.getText().toString();
                final String payAmount = moneyAmontEt.getText().toString();
                if (TextUtils.isEmpty(address) || TextUtils.isEmpty(payAmount) || TextUtils.isEmpty("" + feeEt.getText())) {
                    ToastUtils.showLong(R.string.enter_need_first);
                    return;
                } else if (!AddressUtil.addressIsValid(address)) {
                    ToastUtils.showLong(R.string.addr_invaliad);
                    return;
                }
                Double amountBig = new Double(payAmount);
                final long amountL = (long) (amountBig * 100000000);
                final double fee = new Double("" + feeEt.getText());
                final long feeL = (long) (fee * 100000000);
                if (amountL < 0) {
                    ToastUtils.showLong(R.string.amount_invaliad);
                    return;
                } else if (fee > 0.2) {
                    ToastUtils.showLong(R.string.fee_invaliad);
                    return;
                }
                dialog.setOnDismissListener(dialog.new PwdDismissListener() {
                    @Override
                    public void onGetStorage(final Storage storage) {
                        if (null == storage) return;
                        final Utils.PayResCallback callback = new Utils.PayResCallback() {
                            @Override
                            public void onRes(final String result) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (null == result || result.contains("error")) {
                                            ToastUtils.showLong("tx failed!");
                                        } else {
                                            ToastUtils.showLong("tx successed!");
                                        }
                                        finish();
                                    }
                                });
                            }
                        };
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                Utils.pay(address, amountL + "", storage, callback, feeL);
                            }
                        };
                        String tipText = ApplicationContext.getInstance().isLatestBlock() ? getString(R.string.toast_bch_tx_start) : getString(R.string.toast_header_synchnizing);
                        ToastUtils.showShort(tipText);
                        new Thread(runnable).start();
                    }
                });
                dialog.show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    addrDetail.setText(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(SendActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}

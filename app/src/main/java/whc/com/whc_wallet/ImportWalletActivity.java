package whc.com.whc_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import java.io.IOException;
import java.util.List;

import whc.com.whc_wallet.util.Utils;
import whc.com.whc_wallet.view.CustomerDialog;
import whc.com.whc_wallet.view.ShellTokenizer;
import core.core.AddressUtil;
import core.core.Main;
import core.core.NoSecureStorage;
import core.core.Seed;
import core.core.Storage;
import core.util.Constants;


public class ImportWalletActivity extends BaseActivity {

    private ImageView mNameIv;
    private EditText mWalletNameEt;
    private ImageView mLockIv;
    private EditText mPassWordEt;
    private EditText mRepeatPwdEt;
    private CheckBox mCheckBox;
    private String walletName;
    private Storage mStorage;
    private String password;
    private MultiAutoCompleteTextView seedTv;
    private List<String> words2048;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_wallet);
        setTilte(getString(R.string.import_wallet));
        bindViews();
    }

    private void bindViews() {
        mNameIv = (ImageView) findViewById(R.id.nameIv);
        mWalletNameEt = (EditText) findViewById(R.id.walletNameEt);
        mLockIv = (ImageView) findViewById(R.id.lockIv);
        mPassWordEt = (EditText) findViewById(R.id.passWordEt);
        mRepeatPwdEt = (EditText) findViewById(R.id.repeatPwdEt);
        mCheckBox = findViewById(R.id.checkbox);

        final String seedWord = getIntent().getStringExtra(SeedWordShowActivity.EXTRA_SEED);

        words2048 = new Seed().getWordList();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                words2048);

        seedTv = (MultiAutoCompleteTextView) findViewById(R.id.showSeedTv);
        seedTv.setTokenizer(new ShellTokenizer(this, ' ', true));
        seedTv.setAdapter(adapter);

        setEditTextInhibitInputSpace(seedTv);

        findViewById(R.id.service_protocol_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ImportWalletActivity.this, UserAgreementActivity.class);
                startActivity(it);
            }
        });

        findViewById(R.id.privacy_policy_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ImportWalletActivity.this, PrivacyPolicyActivity.class);
                startActivity(it);
            }
        });


        findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = "" + mPassWordEt.getText();
                walletName = "" + mWalletNameEt.getText();
                if (password.length() < 8 || !Utils.isValidPassword(password.toString())) {
                    LogUtils.d("Not Valid");
                    CustomerDialog dialog = new CustomerDialog(ImportWalletActivity.this, getString(R.string.please_enter_correct_pwd), getString(R.string.password_hint));
                    dialog.show();
                    return;
                } else {
                    LogUtils.d("Valid");
                }
                String repeatPwd = mRepeatPwdEt.getText().toString();
                if (!password.equals(repeatPwd)) {
                    CustomerDialog dialog = new CustomerDialog(ImportWalletActivity.this, getString(R.string.pwd_not_match), "");
                    dialog.show();
                    return;
                }
                if (TextUtils.isEmpty(walletName)) {
                    CustomerDialog dialog = new CustomerDialog(ImportWalletActivity.this, getString(R.string.all_need_enter), "");
                    dialog.show();
                    return;
                }
                if (!mCheckBox.isChecked()) {
                    CustomerDialog dialog = new CustomerDialog(ImportWalletActivity.this, getString(R.string.neet_user_aggrement), "");
                    dialog.show();
                    return;
                }
                String seed = seedTv.getText().toString().trim();
                String wordsSplited[] = seed.split(" ");

                if (wordsSplited.length != 12) {
                    CustomerDialog dialog = new CustomerDialog(ImportWalletActivity.this, getString(R.string.seed_num_error), "");
                    dialog.show();
                    return;
                }

                for (String word : wordsSplited) {
                    if (!words2048.contains(word.trim())) {
                        CustomerDialog dialog = new CustomerDialog(ImportWalletActivity.this, getString(R.string.seed_not_contain), "");
                        dialog.show();
                        return;
                    }
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort("Importing, wait for a moment...");
                        initWallet();
                    }
                }).start();

            }
        });
    }

    /**
     * 禁止EditText输入空格
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText){
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals(" ") || source.equals("\n")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    private void initWallet() {
        try {
            Utils.setCurrentWalletName(ImportWalletActivity.this, walletName);
            if (!setStorageInfo(password)) {
                return;
            }
            checkOrGenerateAddress();
            NoSecureStorage storage = NoSecureStorage.getInstance(Constants.STORAGE_NO_SECURE_PATH + walletName.hashCode());
            if (!TextUtils.isEmpty(walletName)) {
                storage.put(Constants.STORAGE_WALLET_NAME, walletName);
                storage.write();
            }
            Intent it = new Intent(ImportWalletActivity.this, MainActivityT.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            String seedWord = mStorage.get("seed", "");
            it.putExtra(SeedWordShowActivity.EXTRA_SEED, seedWord);
            startActivity(it);

        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void checkOrGenerateAddress() throws JSONException, IOException, InvalidCipherTextException {
        String walletName = Utils.getCurrentWalletName(this);
        NoSecureStorage noSecureStorage = NoSecureStorage.getInstance(Constants.STORAGE_NO_SECURE_PATH + walletName.hashCode());
        mStorage = new Storage(Utils.getSecureStoragePathCurrent(), password);
        JSONObject jsonObject = mStorage.get("keystore", new JSONObject());
        if(jsonObject.length() == 0) {
            Main main = new Main();
            String keys[] = main.getKeys(mStorage.get("seed", ""));
            jsonObject.put("xprv", keys[0]);
            jsonObject.put("xpub", keys[1]);
            mStorage.put("keystore", jsonObject);
        }
        JSONObject addresses = noSecureStorage.get("addresses", new JSONObject());
        JSONObject addr_history = noSecureStorage.get("addr_history", new JSONObject());
        JSONArray change = addresses.optJSONArray("change");
        JSONArray changeTestNet = addresses.optJSONArray(Constants.STORAGE_ADDR_CHANGE_NEW);
        if(change== null || change.length() == 0) {
            AddressUtil addressUtil = new AddressUtil(jsonObject.getString("xpub"));
            String[] changeAddresses;
            changeAddresses = addressUtil.generateLegacyAddresses(1, AddressUtil.ADDRESS_TYPE_CHANGE);
            change = new JSONArray();
            for(int i =0 ;i<changeAddresses.length;i++) {
                change.put(changeAddresses[i]);
                addr_history.put(changeAddresses[i], new JSONArray());
            }

            String[] changeAddressesTest;
            changeAddressesTest = addressUtil.generateCashAddresses(1, AddressUtil.ADDRESS_TYPE_CHANGE);
            changeTestNet = new JSONArray();
            for(int i =0 ;i<changeAddressesTest.length;i++) {
                changeTestNet.put(changeAddressesTest[i]);
            }

            addresses.put("change", change);
            addresses.put(Constants.STORAGE_ADDR_CHANGE_NEW, changeTestNet);
            noSecureStorage.put("addresses", addresses);
        }
        JSONArray receiving = addresses.optJSONArray("receiving");
        JSONArray receivingNew = addresses.optJSONArray(Constants.STORAGE_ADDR_RECEIVE_NEW);
        if(receiving== null || receiving.length() == 0) {
            AddressUtil addressUtil = new AddressUtil(jsonObject.getString("xpub"));
            String[] receivingAddresses;
            receivingAddresses = addressUtil.generateLegacyAddresses(1, AddressUtil.ADDRESS_TYPE_RECEIVING);
            receiving = new JSONArray();
            for(int i =0 ;i<receivingAddresses.length;i++) {
                receiving.put(receivingAddresses[i]);
                addr_history.put(receivingAddresses[i], new JSONArray());
            }
            addresses.put("receiving", receiving);

            String[] receivingAddressesNew;
            receivingNew = new JSONArray();
            receivingAddressesNew = addressUtil.generateCashAddresses(1, AddressUtil.ADDRESS_TYPE_RECEIVING);
            for(int i =0 ;i<receivingAddressesNew.length;i++) {
                receivingNew.put(receivingAddressesNew[i]);
            }

            addresses.put(Constants.STORAGE_ADDR_RECEIVE_NEW, receivingNew);
            String receiveAddr = receivingNew.get(0).toString();
            Utils.setAddrOfWallet(walletName, receiveAddr);
            noSecureStorage.put("addr_history", addr_history);
            noSecureStorage.put("addresses", addresses);
        }
        mStorage.write();
        noSecureStorage.write();
    }

    private boolean setStorageInfo(String password) {
        try {

            String seed = seedTv.getText().toString().trim();

            mStorage = new Storage(Utils.getSecureStoragePathCurrent(), password);
            if (TextUtils.isEmpty(seed)) {
                seed = new Seed().generateSeed();
            }
            if(seed != null && seed.length() > 0) {
                mStorage.put("seed", seed);
                mStorage.write();
            }

            return true;
        } catch (InvalidCipherTextException e) {
//            sendErrorAlert("Password Is not correct");
            return false;
        } catch (IOException e) {
//            sendErrorAlert(e.getMessage());
            return false;
        }
    }


}

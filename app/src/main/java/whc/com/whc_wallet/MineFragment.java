package whc.com.whc_wallet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import java.io.IOException;

import whc.com.whc_wallet.util.Utils;
import whc.com.whc_wallet.view.QrCodeDialog;
import core.core.NoSecureStorage;
import core.util.Constants;

import static android.content.Context.CLIPBOARD_SERVICE;

public class MineFragment extends Fragment {
    private RelativeLayout mHistoryLy;
    private RelativeLayout mNoticeCenter;
    private RelativeLayout mLanguageChoooseLy;
    private RelativeLayout mChangePwdLy;
    private RelativeLayout mUserAgreementLy;
    private RelativeLayout mAboutUsLy;
    private RelativeLayout mLookSeed;
    private View mQrCodeV;
    private TextView mAddrTv;
    private String mWalletName;
    private TextView mNameTv;
    private boolean mShowNoticeRedIcon = false;
    private String mReceiveAddr;
    private View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.mine_fragment, container, false);
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mQrCodeV = (View) mContentView.findViewById(R.id.qrCodeV);
        mNameTv = (TextView) mContentView.findViewById(R.id.nameTv);
        mAddrTv = (TextView) mContentView.findViewById(R.id.addrTv);

        mHistoryLy = (RelativeLayout) getActivity().findViewById(R.id.historyLy);
        mNoticeCenter = (RelativeLayout) getActivity().findViewById(R.id.noticeCenter);
        mLanguageChoooseLy = (RelativeLayout) getActivity().findViewById(R.id.languageChoooseLy);
        mChangePwdLy = (RelativeLayout) getActivity().findViewById(R.id.changePwdLy);
        mUserAgreementLy = (RelativeLayout) getActivity().findViewById(R.id.userAgreementLy);
        mAboutUsLy = (RelativeLayout) getActivity().findViewById(R.id.aboutUsLy);
        mLookSeed = (RelativeLayout) getActivity().findViewById(R.id.lookSeed);

        mAddrTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ((TextView)v).getText().toString();
                ClipData myClip = ClipData.newPlainText("text", text);
                // 获取系统剪贴板
                ClipboardManager myClipboard =
                        (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                myClipboard.setPrimaryClip(myClip);
                ToastUtils.showLong(R.string.copy_already);
            }
        });
        mQrCodeV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QrCodeDialog dialog = new QrCodeDialog(getActivity(), mWalletName, mReceiveAddr);
                dialog.show();
            }
        });
        mNoticeCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), NoticeCenterActivity.class);
                getActivity().startActivity(it);
            }
        });
        mHistoryLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TxHistoryActivity.class);
                getActivity().startActivity(it);
            }
        });
        mLanguageChoooseLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ChooseLanguageActivity.class);
                getActivity().startActivity(it);
            }
        });
        mChangePwdLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ChangePwdActivity.class);
                getActivity().startActivity(it);
            }
        });
        mAboutUsLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), AboutUsActivity.class);
                getActivity().startActivity(it);
            }
        });
        mUserAgreementLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), UserAgreementActivity.class);
                getActivity().startActivity(it);
            }
        });
        mLookSeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), SeedWordLookActivity.class);
                getActivity().startActivity(it);
            }
        });

        getDatas();

    }

    @Override
    public void onResume() {
        super.onResume();
        String currWalName = Utils.getCurrentWalletName();
        if (!currWalName.equals(mWalletName)) {
            getDatas();
        }
    }

    private void getDatas() {
        mWalletName = Utils.getCurrentWalletName(getActivity());
        mNameTv.setText(mWalletName);
        try {
            NoSecureStorage storage = NoSecureStorage.getInstance(Constants.STORAGE_NO_SECURE_PATH + mWalletName.hashCode());
            JSONObject addresses = storage.get("addresses", new JSONObject());
            JSONArray array = addresses.optJSONArray(Constants.STORAGE_ADDR_RECEIVE_NEW);
            mReceiveAddr = array.get(0).toString();
            mAddrTv.setText(mReceiveAddr);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getActivity().findViewById(R.id.redIconInMine).setVisibility(mShowNoticeRedIcon ? View.VISIBLE : View.GONE);
    }

    public void showNoticeRedIcon() {
         if (null == getActivity()) {
            mShowNoticeRedIcon = true;
         } else {
            getActivity().findViewById(R.id.redIconInMine).setVisibility(View.VISIBLE);
         }
    }

    public void hideNoticeRedIcon() {
        getActivity().findViewById(R.id.redIconInMine).setVisibility(View.GONE);
    }
}

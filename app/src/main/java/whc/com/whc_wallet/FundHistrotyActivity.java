package whc.com.whc_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import whc.com.whc_wallet.adapter.FundHistoryAdapter;
import whc.com.whc_wallet.util.Utils;
import whc.com.whc_wallet.view.QrCodeDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wormhole.net.Retrofit;
import wormhole.net.response.Balance;
import wormhole.net.response.BasePageResponse;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.HistrotyRes;
import wormhole.net.response.PropertyData;

public class FundHistrotyActivity extends BaseActivity {
    public static final String EXTRA_FUNDING = "extra_funding";

    private PullLoadMoreRecyclerView mRecyclerView;
    private FundHistoryAdapter mAdapter;
    private Balance mFunding;
    private boolean mFetchedAll;
    private List<HistrotyRes> mHistoryList = new ArrayList<>();
    private Button mRecieveBtn;
    private Button mSendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_history);
        bindViews();
        getDatas();
    }

    private void getDatas() {
        String fundingJson = getIntent().getStringExtra(FundHistrotyActivity.EXTRA_FUNDING);
        mFunding = new Gson().fromJson(fundingJson, Balance.class);
        mAdapter = new FundHistoryAdapter(mHistoryList, this);
        mAdapter.setHeaderData(mFunding);
        mRecyclerView.setAdapter(mAdapter);
        fetchNet(mFunding);
    }

    private void bindViews() {
        setTilte(getString(R.string.tx_history_no_colon));
        mRecieveBtn = (Button) findViewById(R.id.recieveBtn);
        mSendBtn = (Button) findViewById(R.id.sendBtn);
        mRecyclerView = (PullLoadMoreRecyclerView)findViewById(R.id.overview_recycler_view);
        mRecyclerView.setPullRefreshEnable(false);
        mRecyclerView.setFooterViewText("loading");
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                fetchNet(mFunding);

            }
        });

        mAdapter = new FundHistoryAdapter(mHistoryList, this);
        mAdapter.setHeaderData(mFunding);
        mRecyclerView.setLinearLayout();
        mRecyclerView.setAdapter(mAdapter);
        mRecieveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QrCodeDialog dialog = new QrCodeDialog(FundHistrotyActivity.this,
                        Utils.getCurrentWalletName(FundHistrotyActivity.this), Utils.getNewStyleAddressUsing());
                dialog.show();
            }
        });
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("BCH".equals(mFunding.property_name)) {
                    Intent it = new Intent(FundHistrotyActivity.this, SendActivity.class);
                    startActivity(it);
                } else {
                    PropertyData propertyData = new PropertyData();
                    propertyData.issuer = mFunding.address;
                    propertyData.propertyid = mFunding.property_id;
                    Intent it = new Intent(FundHistrotyActivity.this, SendTokenActivity.class);
                    it.putExtra(FundingDetailActivity.EXTRA_FUNDING, new Gson().toJson(propertyData));
                    startActivity(it);
                }
            }
        });
    }

    public void fetchNet(Balance funding) {
        if (mFetchedAll){
            mRecyclerView.setPullLoadMoreCompleted();
            return;
        }
        Map<String, Object> filedMap = new HashMap<>();
        int pageSize = 20;
        int pageNo = mHistoryList.size() / pageSize + 1;
        filedMap.put("pageNo", pageNo);
        filedMap.put("pageSize", pageSize);
        filedMap.put("property_id", funding.property_id);
        mRecyclerView.setRefreshing(true);
        filedMap.put("address", Utils.getNewStyleAddressUsing());

        Call<BaseResponse<BasePageResponse<HistrotyRes>>> call = Retrofit.getRetrofit().getService().historyList(filedMap, pageSize, pageNo);
        call.enqueue(new Callback<BaseResponse<BasePageResponse<HistrotyRes>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BasePageResponse<HistrotyRes>>> call, Response<BaseResponse<BasePageResponse<HistrotyRes>>> response) {
                if (!Utils.dealCommonNetRes(response)) return;
                if (null == response.body().result.list || response.body().result.list.size() < 1) {
                    ToastUtils.showLong(getString(R.string.no_msg));
                    return;
                }
                mHistoryList.addAll(response.body().result.list);
                mFetchedAll = response.body().result.total <= mHistoryList.size();
                mAdapter.updateData(mHistoryList);
                mRecyclerView.setPullLoadMoreCompleted();

            }

            @Override
            public void onFailure(Call<BaseResponse<BasePageResponse<HistrotyRes>>> call, Throwable t) {
                mRecyclerView.setPullLoadMoreCompleted();

            }

        });

    }



}

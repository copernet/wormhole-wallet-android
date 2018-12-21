package whc.com.whc_wallet;

import android.os.Bundle;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import whc.com.whc_wallet.adapter.HistoryListAdapter;
import whc.com.whc_wallet.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wormhole.net.Retrofit;
import wormhole.net.request.OpreateFund;
import wormhole.net.response.BasePageResponse;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.HistrotyRes;

public class TxHistoryActivity extends BaseActivity {

    private OpreateFund mOpreateFund;
    private PullLoadMoreRecyclerView mRecyclerView;
    private HistoryListAdapter mAdapter;
    private List<HistrotyRes> mHistoryList = new ArrayList<>();
    private boolean mFetchedAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tx_history);
        bindViews();
        getDatas();
    }

    private void getDatas() {
        fetchNet();
    }

    private void bindViews() {
        setTilte(getString(R.string.tx_history_no_colon));
        mRecyclerView = (PullLoadMoreRecyclerView)findViewById(R.id.overview_recycler_view);
        mRecyclerView.setPullRefreshEnable(false);
        mRecyclerView.setFooterViewText("loading");
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                fetchNet();

            }
        });
        mAdapter = new HistoryListAdapter(mHistoryList, TxHistoryActivity.this);;
        mRecyclerView.setLinearLayout();
        mRecyclerView.setAdapter(mAdapter);
    }

    public void fetchNet() {
        if (mFetchedAll) {
            mRecyclerView.setPullLoadMoreCompleted();
            return;
        }
        Map<String, Object> filedMap = new HashMap<>();
        int pageSize = 20;
        int pageNo = mHistoryList.size() / pageSize + 1;
        filedMap.put("pageNo", pageNo);
        filedMap.put("pageSize", pageSize);
        mRecyclerView.setRefreshing(true);
        String str = Utils.getNewStyleAddressUsing();
        filedMap.put("address", str);

        Call<BaseResponse<BasePageResponse<HistrotyRes>>> call = Retrofit.getRetrofit().getService().historyList(filedMap, pageSize, pageNo);
        call.enqueue(new Callback<BaseResponse<BasePageResponse<HistrotyRes>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BasePageResponse<HistrotyRes>>> call, Response<BaseResponse<BasePageResponse<HistrotyRes>>> response) {
                mRecyclerView.setPullLoadMoreCompleted();
                showContentView();
                if (!Utils.dealCommonNetRes(response)) return;
                if(!dealCommonListRes(response, mHistoryList)) return;
                mHistoryList.addAll(response.body().result.list);
                mFetchedAll = response.body().result.total <= mHistoryList.size();
                mAdapter.updateData(mHistoryList);

//                setContentView(R.layout.activity_base);

            }

            @Override
            public void onFailure(Call<BaseResponse<BasePageResponse<HistrotyRes>>> call, Throwable t) {
                mRecyclerView.setPullLoadMoreCompleted();
                showNetErrorView();
            }

        });

    }

//    public String getSearchWord() {
//        return mSearchWord;
//    }



}

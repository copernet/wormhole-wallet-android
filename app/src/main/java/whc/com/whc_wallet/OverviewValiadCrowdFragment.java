package whc.com.whc_wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import whc.com.whc_wallet.adapter.OverviewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import whc.com.whc_wallet.adapter.ValidCrowdAdapter;
import whc.com.whc_wallet.util.Utils;
import wormhole.net.Retrofit;
import wormhole.net.response.BasePageResponse;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.PropertyData;

public class OverviewValiadCrowdFragment extends BaseFragment {

    private PullLoadMoreRecyclerView mRecyclerView;
    private ArrayList<PropertyData> fundingList = new ArrayList<>();
    private String mSearchWord = "";
    private ValidCrowdAdapter mAdapter;
    private boolean mFetchedAll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview_pull, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
        getDatas();
    }

    private void getDatas() {
        fetchNet();
    }

    private void bindViews() {
        mRecyclerView = (PullLoadMoreRecyclerView)getView().findViewById(R.id.overview_recycler_view);
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
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new ValidCrowdAdapter(fundingList, getActivity(), false);
//        mRecyclerView.setLayoutManager(mLayoutManager);
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
        int pageNo = fundingList.size() / pageSize + 1;
        filedMap.put("pageNo", pageNo);
        filedMap.put("pageSize", pageSize);
        filedMap.put("keyword", mSearchWord);
        mRecyclerView.setRefreshing(true);

        Call<BaseResponse<BasePageResponse<PropertyData>>> call =  Retrofit.getRetrofit().getService().fetchActiveCrowd(filedMap);
        call.enqueue(new Callback<BaseResponse<BasePageResponse<PropertyData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BasePageResponse<PropertyData>>> call, Response<BaseResponse<BasePageResponse<PropertyData>>> response) {

                mRecyclerView.setPullLoadMoreCompleted();
                if (!dealCommonListRes(response, fundingList)) return;
                showContentView();
                fundingList.addAll(response.body().result.list);
                mAdapter.updateData(fundingList);
                mFetchedAll = response.body().result.total <= fundingList.size();
            }

            @Override
            public void onFailure(Call<BaseResponse<BasePageResponse<PropertyData>>> call, Throwable t) {
                mRecyclerView.setPullLoadMoreCompleted();
                showNetErrorView();

            }
        });

    }

    public String getSearchWord() {
        return mSearchWord;
    }

    public void setSearchWord(String searchWord) {
        if (!searchWord.equals(mSearchWord)) {
            this.mSearchWord = searchWord;
            mFetchedAll = false;
            fundingList.clear();
            mAdapter.notifyDataSetChanged();
            fetchNet();
        }
    }

}

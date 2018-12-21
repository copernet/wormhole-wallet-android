package whc.com.whc_wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import whc.com.whc_wallet.adapter.FundDetailAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import whc.com.whc_wallet.util.Utils;
import wormhole.net.Retrofit;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.PropertyData;
import wormhole.net.response.FundingTxData;
import wormhole.net.response.TxData;

public class FundingDetailActivity extends BaseFragment {
    public static final String EXTRA_FUNDING = "extra_funding";
    public static final String EXTRA_SHOW_OPERALY = "extra_show_opera";

    private RecyclerView mRecyclerView;
    private FundDetailAdapter mAdapter;
    private PropertyData mFunding;

    public void setFunding(PropertyData funding) {
        mFunding = funding;
    }

    @Override
    public void onViewCreated(@org.jetbrains.annotations.Nullable View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
        getDatas();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fund_detail, container,false);
        return v;
    }


    private void bindViews() {
        mRecyclerView = getView().findViewById(R.id.overview_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    private void getDatas() {
        mAdapter = new FundDetailAdapter(new ArrayList<TxData>(), getContext());
        mRecyclerView.setAdapter(mAdapter);
        fetchNet(mFunding);
    }

    public void fetchNet(final PropertyData funding) {

        Call<BaseResponse<FundingTxData>> call =  Retrofit.getRetrofit().getService().txOfFunding(funding.propertyid, 0, 20);
        call.enqueue(new Callback<BaseResponse<FundingTxData>>() {
            @Override
            public void onResponse(Call<BaseResponse<FundingTxData>> call, Response<BaseResponse<FundingTxData>> response) {
                if (!Utils.dealCommonNetRes(response)) return;
                List<TxData> fundingList = response.body().result.transactions;
                mAdapter.updateData(fundingList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<BaseResponse<FundingTxData>> call, Throwable t) {

            }
        });

    }

    @org.jetbrains.annotations.Nullable
    @Override
    public RecyclerView getRecyclerView() {
        if (null == getView()) {
            return null;
        }
        return (getView().findViewById(R.id.overview_recycler_view));
    }


}

package whc.com.whc_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import whc.com.whc_wallet.adapter.OverviewAdapter;
import whc.com.whc_wallet.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wormhole.net.Retrofit;
import wormhole.net.response.BasePageResponse;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.PropertyData;
import wormhole.net.response.ListbyownerRes;

public class FundFragment extends Fragment {

    private TextView mTitleBarTv;
    private TextView mTitleBarRightIv;
    private RelativeLayout mContentLy;

    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private View mAddFundFloatBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fund, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getDatas();
        }
    }

    private void getDatas() {
        fetchNet();
    }

    private void bindViews() {
        mRecyclerView = getActivity().findViewById(R.id.fund_recycler_view);
        mEmptyView = getActivity().findViewById(R.id.emptyView);
        mTitleBarTv = (TextView) getActivity().findViewById(R.id.titleBarTv);
        mTitleBarRightIv = (TextView) getActivity().findViewById(R.id.titleBarRightTv);
        mContentLy = (RelativeLayout) getActivity().findViewById(R.id.contentLy);
        mAddFundFloatBtn = getActivity().findViewById(R.id.addFundFloatBtn);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        OverviewAdapter mAdapter = new OverviewAdapter(new ArrayList<PropertyData>(), getActivity(), true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mEmptyView.findViewById(R.id.addFund).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), CreateFundActivity.class);
                startActivity(it);
            }
        });
        mAddFundFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), CreateFundActivity.class);
                startActivity(it);
            }
        });
        mTitleBarRightIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), SendAllActivity.class);
                startActivity(it);
            }
        });
    }

    public void fetchNet() {
        String addr = Utils.getTestAddress();
        String addrs[] = new String[]{addr};

        Call<BaseResponse<BasePageResponse<ListbyownerRes>>> call =  Retrofit.getRetrofit().getService().listByOwner(addrs);
        call.enqueue(new Callback<BaseResponse<BasePageResponse<ListbyownerRes>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BasePageResponse<ListbyownerRes>>> call, Response<BaseResponse<BasePageResponse<ListbyownerRes>>> response) {
                if (!Utils.dealCommonNetRes(response)) return;
                ArrayList<ListbyownerRes> listbyownerResList = response.body().result.list;
                if (listbyownerResList.size() <= 0 ) {
                    showEmptyView();
                } else {
                    hideEmptyView();
                }

                ArrayList<PropertyData> fundingList = new ArrayList<>();
                for (ListbyownerRes res : listbyownerResList) {
                    fundingList.add(res.PropertyData);
                }
                OverviewAdapter mAdapter = new OverviewAdapter(fundingList, getActivity(), true);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<BaseResponse<BasePageResponse<ListbyownerRes>>> call, Throwable t) {

            }
        });

    }

    private void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mContentLy.setVisibility(View.GONE);
        mTitleBarRightIv.setVisibility(View.GONE);
    }

    private void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
        mContentLy.setVisibility(View.VISIBLE);
        mTitleBarRightIv.setVisibility(View.VISIBLE);
    }

}

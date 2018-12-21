package whc.com.whc_wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import whc.com.whc_wallet.adapter.FundDetailAdapter;
import whc.com.whc_wallet.adapter.FundDetailOwnersAdapter;
import whc.com.whc_wallet.util.Utils;
import wormhole.net.Retrofit;
import wormhole.net.response.BasePageResponse;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.FundingTxData;
import wormhole.net.response.OwnersData;
import wormhole.net.response.PropertyData;
import wormhole.net.response.TxData;

public class FundingDetailOwnersFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private FundDetailOwnersAdapter mAdapter;
    private PropertyData mFunding;
    private ArrayList<PropertyData> fundingList = new ArrayList<>();

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
        mAdapter = new FundDetailOwnersAdapter(new ArrayList<OwnersData>(), getContext());
        mAdapter.setFunding(mFunding);
        mRecyclerView.setAdapter(mAdapter);
        fetchNet(mFunding);
    }

    public void fetchNet(final PropertyData funding) {

        Map<String, Object> filedMap = new HashMap<>();
        int pageSize = 20;
        filedMap.put("address", "bchtest:qq6qag6mv2fzuq73qanm6k60wppy23djnv7ddk3lpk");
//        filedMap.put("address", Utils.getNewStyleAddressUsing());

        Call<BaseResponse<BasePageResponse<OwnersData>>> call =  Retrofit.getRetrofit().getService().ownersOfFunding(459, 20, 0, 0);
        call.enqueue(new Callback<BaseResponse<BasePageResponse<OwnersData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BasePageResponse<OwnersData>>> call, Response<BaseResponse<BasePageResponse<OwnersData>>> response) {
                if (!Utils.dealCommonNetRes(response)) return;
                ArrayList<OwnersData> fundingList = response.body().result.list;
                mAdapter.updateData(fundingList);

            }

            @Override
            public void onFailure(Call<BaseResponse<BasePageResponse<OwnersData>>> call, Throwable t) {

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

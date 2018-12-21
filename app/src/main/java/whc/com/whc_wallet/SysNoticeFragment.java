package whc.com.whc_wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import whc.com.whc_wallet.adapter.NoticeAdapter;

public class SysNoticeFragment extends BaseFragment {


    private PullLoadMoreRecyclerView mRecyclerView;
    private NoticeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tx_history, container,false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindViews();
        getDatas();
        MainActivityT.sMainActivity.hideNoticeRedIcon();
    }

    private void bindViews() {
        mRecyclerView = (PullLoadMoreRecyclerView)getView().findViewById(R.id.overview_recycler_view);
        mRecyclerView.setPullRefreshEnable(false);
        mRecyclerView.setPushRefreshEnable(false);
        mRecyclerView.setFooterViewText("loading");
        mRecyclerView.setLinearLayout();
    }

    private void getDatas() {
       showNoMsgView();
    }

}

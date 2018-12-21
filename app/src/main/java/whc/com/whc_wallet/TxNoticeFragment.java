package whc.com.whc_wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import whc.com.whc_wallet.adapter.NoticeAdapter;
import whc.com.whc_wallet.util.Utils;
import wormhole.net.response.Notice;

public class TxNoticeFragment extends BaseFragment {

    public static List<Notice> sNoticeList = new ArrayList<>();

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
        Utils.updateNoticeTime();
        if (null == TxNoticeFragment.sNoticeList || TxNoticeFragment.sNoticeList.size() < 1) {
            showNoMsgView();
            return;
        }
        mAdapter = new NoticeAdapter(TxNoticeFragment.sNoticeList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setPullLoadMoreCompleted();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public RecyclerView getRecyclerView() {
        return ((PullLoadMoreRecyclerView)getView().findViewById(R.id.overview_recycler_view)).getRecyclerView();
    }
}

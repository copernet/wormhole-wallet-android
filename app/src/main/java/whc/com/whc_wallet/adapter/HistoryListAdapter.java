package whc.com.whc_wallet.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import whc.com.whc_wallet.R;
import whc.com.whc_wallet.TxDetailActivity;
import whc.com.whc_wallet.databinding.ItemTxHistory2Binding;
import wormhole.net.response.HistrotyRes;

/**
 * Created by chuanbei.qiao on 2018/10/24.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HistrotyRes> mData;
    private Context mContext;

    public HistoryListAdapter(List<HistrotyRes> histrotyResList, Context context) {
        mData = histrotyResList;
        mContext = context;
    }

    public void updateData(List<HistrotyRes> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTxHistory2Binding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_tx_history_2, parent, false);
        ViewHolder holder = new ViewHolder(binding);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent it = new Intent(mContext, TxDetailActivity.class);
                    HistrotyRes txData = (HistrotyRes) v.getTag();
                    it.putExtra(TxDetailActivity.EXTRA_TX_HASH, txData.tx_hash);
                    mContext.startActivity(it);
            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HistrotyRes crowdFunding = mData.get(position);
        holder.itemView.setTag(crowdFunding);
        ((ViewHolder) holder).getBinding().setHistoryRes(crowdFunding);
        ((ViewHolder) holder).getBinding().executePendingBindings();

    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTxHistory2Binding binding;

        public ItemTxHistory2Binding getBinding() {
            return binding;
        }

        public ViewHolder(ItemTxHistory2Binding binding) {
            super(binding.getRoot());
            AutoUtils.autoSize(binding.getRoot());
            this.binding = binding;
        }
    }

}

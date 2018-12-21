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
import whc.com.whc_wallet.databinding.HeaderCoinHistoryBinding;
import whc.com.whc_wallet.databinding.ItemTxHistory2Binding;
import wormhole.net.response.Balance;
import wormhole.net.response.HistrotyRes;

/**
 * Created by chuanbei.qiao on 2018/10/24.
 */

public class FundHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HistrotyRes> mData;
    private Context mContext;
    private Balance mHeaderData;

    public FundHistoryAdapter(List<HistrotyRes> data, Context context) {
        this.mData = data;
        mContext = context;
    }

    public void updateData(List<HistrotyRes> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setHeaderData(Balance funding) {
        this.mHeaderData = funding;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (1 == viewType) {
            ItemTxHistory2Binding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_tx_history_2, parent, false);
            ViewHolder holder = new ViewHolder(binding);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(mContext, TxDetailActivity.class);
                    HistrotyRes txData = (HistrotyRes) v.getTag();
                    it.putExtra(TxDetailActivity.EXTRA_TX_HASH, txData.tx_hash);
                    if (0 == mHeaderData.property_id) {
                        return;
                    }
                    mContext.startActivity(it);
                }
            });
            return holder;
        } else {
            HeaderCoinHistoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.header_coin_history, parent, false);
            binding.setBalance(mHeaderData);
            HeaderViewHolder holder = new HeaderViewHolder(binding);
            return holder;

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position > 0) {
            HistrotyRes crowdFunding = mData.get(position - 1);
            holder.itemView.setTag(crowdFunding);
            ((ViewHolder) holder).getBinding().setHistoryRes(crowdFunding);
            ((ViewHolder) holder).getBinding().executePendingBindings();
        } else if (0 == position){
//            ((HeaderViewHolder)holder).setType(mHeaderData.getPropertyType());
        }

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
        return mData == null ? 1 : mData.size()  + 1;
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

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private HeaderCoinHistoryBinding binding;

        public HeaderCoinHistoryBinding getBinding() {
            return binding;
        }

        public HeaderViewHolder(HeaderCoinHistoryBinding binding) {
            super(binding.getRoot());
            AutoUtils.autoSize(binding.getRoot());
            this.binding = binding;
        }

    }


}

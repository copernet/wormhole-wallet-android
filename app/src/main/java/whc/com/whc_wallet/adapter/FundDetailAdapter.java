package whc.com.whc_wallet.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import whc.com.whc_wallet.AirDropActivity;
import whc.com.whc_wallet.ChangeOwnerActivity;
import whc.com.whc_wallet.CloseFundActivity;
import whc.com.whc_wallet.DestroyFundActivity;
import whc.com.whc_wallet.FundingDetailActivity;
import whc.com.whc_wallet.JoinCrowdActivityT;
import whc.com.whc_wallet.R;
import whc.com.whc_wallet.SendTokenActivity;
import whc.com.whc_wallet.SeoActivity;
import whc.com.whc_wallet.TxDetailActivity;
import whc.com.whc_wallet.databinding.FundDetailHeaderBinding;
import whc.com.whc_wallet.databinding.ItemTxHistoryBinding;
import wormhole.net.response.HistrotyRes;
import wormhole.net.response.PropertyData;
import wormhole.net.response.TxData;

/**
 * Created by chuanbei.qiao on 2018/10/24.
 */

public class FundDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TxData> mData;
    private Context mContext;

    public FundDetailAdapter(List<TxData> data, Context context) {
        this.mData = data;
        mContext = context;
    }

    public void updateData(List<TxData> data) {
        this.mData = data;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemTxHistoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_tx_history, parent, false);
            ViewHolder holder = new ViewHolder(binding);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(mContext, TxDetailActivity.class);
                    TxData txData = (TxData) v.getTag();
                    it.putExtra(TxDetailActivity.EXTRA_TX_HASH, txData.txid);
                    mContext.startActivity(it);
                }
            });
            return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TxData crowdFunding = mData.get(position);
            holder.itemView.setTag(crowdFunding);
            ((ViewHolder) holder).getBinding().setTxData(crowdFunding);
            ((ViewHolder) holder).getBinding().executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTxHistoryBinding binding;

        public ItemTxHistoryBinding getBinding() {
            return binding;
        }

        public ViewHolder(ItemTxHistoryBinding binding) {
            super(binding.getRoot());
            AutoUtils.autoSize(binding.getRoot());
            this.binding = binding;
        }
    }


}

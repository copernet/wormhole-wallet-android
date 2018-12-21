package whc.com.whc_wallet.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import whc.com.whc_wallet.FundingDetailActivity;
import whc.com.whc_wallet.R;
import whc.com.whc_wallet.ThawActivity;
import whc.com.whc_wallet.TxDetailActivity;
import whc.com.whc_wallet.databinding.ItemTxHistoryBinding;
import whc.com.whc_wallet.databinding.ItemTxOwnersBinding;
import wormhole.net.response.OwnersData;
import wormhole.net.response.PropertyData;
import wormhole.net.response.TxData;

/**
 * Created by chuanbei.qiao on 2018/10/24.
 */

public class FundDetailOwnersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OwnersData> mData;
    private Context mContext;
    private PropertyData funding;

    public FundDetailOwnersAdapter(List<OwnersData> data, Context context) {
        this.mData = data;
        mContext = context;
    }

    public void updateData(List<OwnersData> data) {
        this.mData = data;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTxOwnersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_tx_owners, parent, false);
        ViewHolder holder = new ViewHolder(binding);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OwnersData txData = (OwnersData) v.getTag();
                Intent it = null;
                if (0 == txData.Status) {
                    it = new Intent(mContext, ThawActivity.class);
                    it.putExtra(FundingDetailActivity.EXTRA_FUNDING, new Gson().toJson(funding));
                } else {
                    it = new Intent(mContext, ThawActivity.class);
                    it.putExtra(FundingDetailActivity.EXTRA_FUNDING, new Gson().toJson(funding));
                }
                mContext.startActivity(it);
            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OwnersData crowdFunding = mData.get(position);
        holder.itemView.setTag(crowdFunding);
        ItemTxOwnersBinding binding = ((ViewHolder) holder).getBinding();
        ((ViewHolder) holder).getBinding().setTxData(crowdFunding);
        ((ViewHolder) holder).getBinding().executePendingBindings();
        if (0 == crowdFunding.Status) {
            binding.opBtn.setText("Frozen");
            binding.textView6.setText("Thawing");
        } else {
            binding.opBtn.setText("Thawed");
            binding.textView6.setText("Freezing");
        }

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setFunding(PropertyData funding) {
        this.funding = funding;
    }

    public PropertyData getFunding() {
        return funding;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTxOwnersBinding binding;

        public ItemTxOwnersBinding getBinding() {
            return binding;
        }

        public ViewHolder(ItemTxOwnersBinding binding) {
            super(binding.getRoot());
            AutoUtils.autoSize(binding.getRoot());
            this.binding = binding;

        }
    }


}

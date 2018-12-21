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

import java.util.ArrayList;
import java.util.List;

import whc.com.whc_wallet.FundHistrotyActivity;
import whc.com.whc_wallet.R;
import whc.com.whc_wallet.databinding.ItemMainPageBinding;
import wormhole.net.response.Balance;

/**
 * Created by chuanbei.qiao on 2018/10/24.
 */

public class BalanceMainPageAdapter extends RecyclerView.Adapter<BalanceMainPageAdapter.ViewHolder> {

    private List<Balance> mData;
    private Context mContext;
    private boolean mShowBalance = true;

    public BalanceMainPageAdapter(ArrayList<Balance> data, Context context) {
        mContext = context;
        this.mData = data;
    }

    public void updateData(List<Balance> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setShowBalance(boolean show) {
        this.mShowBalance = show;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainPageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_main_page, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext, FundHistrotyActivity.class);
                Balance funding = (Balance) v.getTag();
                it.putExtra(FundHistrotyActivity.EXTRA_FUNDING, new Gson().toJson(funding));
                mContext.startActivity(it);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Balance balance = mData.get(position);
        balance.showBalance = mShowBalance;
        holder.itemView.setTag(balance);
        ((ViewHolder) holder).getBinding().setBalance(balance);
        ((ViewHolder) holder).getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMainPageBinding binding;

        public ItemMainPageBinding getBinding() {
            return binding;
        }

        public ViewHolder(ItemMainPageBinding binding) {
            super(binding.getRoot());
            AutoUtils.autoSize(binding.getRoot());
            this.binding = binding;
        }
    }


}

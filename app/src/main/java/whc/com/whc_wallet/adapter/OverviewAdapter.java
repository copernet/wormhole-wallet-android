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

import whc.com.whc_wallet.FundingDetailActivity;
import whc.com.whc_wallet.MainActivity;
import whc.com.whc_wallet.R;
import whc.com.whc_wallet.databinding.ItemOverviewBinding;
import wormhole.net.response.PropertyData;

/**
 * Created by chuanbei.qiao on 2018/10/24.
 */
public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder> {

    private List<PropertyData> mData;
    private Context mContext;
    private boolean mShowOperaly;

    public OverviewAdapter(ArrayList<PropertyData> data, Context context, boolean isMine) {
        this.mData = data;
        mContext = context;
        mShowOperaly = isMine;
    }

    public void updateData(List<PropertyData> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemOverviewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_overview, parent, false);
        ViewHolder holder = new ViewHolder(binding);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext, MainActivity.class);
                PropertyData funding = (PropertyData) v.getTag();
                it.putExtra(FundingDetailActivity.EXTRA_FUNDING, new Gson().toJson(funding));
                it.putExtra(FundingDetailActivity.EXTRA_SHOW_OPERALY, mShowOperaly);
                mContext.startActivity(it);
            }
        });

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PropertyData propertyData = mData.get(position);
        holder.itemView.setTag(mData.get(position));
        switch (getItemViewType(position)) {
            case PropertyData.FIXED_FUND:
                holder.getBinding().typeIcon.setBackgroundResource(R.drawable.browse_icon_smart);
                holder.getBinding().tokenCatagoryTv.setText("Fixed Token");
                break;
            case PropertyData.MANAGABLE_FUND:
                holder.getBinding().typeIcon.setBackgroundResource(R.drawable.browse_icon_management);
                holder.getBinding().tokenCatagoryTv.setText("Manageable Token");
                break;
            case PropertyData.CROWD_FUND:
                holder.getBinding().typeIcon.setBackgroundResource(R.drawable.browse_icon_crowd);
                holder.getBinding().tokenCatagoryTv.setText("Crowdsale Token");
                break;
        }
        ((ViewHolder) holder).getBinding().setPropertyData(propertyData);
        ((ViewHolder) holder).getBinding().executePendingBindings();

    }

    @Override
    public int getItemViewType(int position) {
        PropertyData property = mData.get(position);
        if (property.fixedissuance) {
            return PropertyData.FIXED_FUND;
        } else if (property.managedissuance) {
            return PropertyData.MANAGABLE_FUND;
        } else {
            return PropertyData.CROWD_FUND;
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemOverviewBinding binding;

        public ItemOverviewBinding getBinding() {
            return binding;
        }

        public ViewHolder(ItemOverviewBinding binding) {
            super(binding.getRoot());
            AutoUtils.autoSize(binding.getRoot());
            this.binding = binding;
        }
    }


}

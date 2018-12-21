package whc.com.whc_wallet.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import whc.com.whc_wallet.R;
import whc.com.whc_wallet.databinding.ItemNoticeBinding;
import wormhole.net.response.Notice;

/**
 * Created by chuanbei.qiao on 2018/10/24.
 */

public class NoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Notice> mData;
    private Context mContext;

    public NoticeAdapter(List<Notice> histrotyResList, Context context) {
        mData = histrotyResList;
        mContext = context;
    }

    public void updateData(List<Notice> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemNoticeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_notice, parent, false);
        ViewHolder holder = new ViewHolder(binding);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Intent it = new Intent(mContext, FundingDetailActivity.class);
//                    TxData txData = (TxData) v.getTag();
//                    it.putExtra(FundingDetailActivity.EXTRA_TX_DATA, new Gson().toJson(txData));
//                    mContext.startActivity(it);
            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Notice crowdFunding = mData.get(position);
        holder.itemView.setTag(crowdFunding);
        ((ViewHolder) holder).getBinding().setNotice(crowdFunding);
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
        private ItemNoticeBinding binding;

        public ItemNoticeBinding getBinding() {
            return binding;
        }

        public ViewHolder(ItemNoticeBinding binding) {
            super(binding.getRoot());
            AutoUtils.autoSize(binding.getRoot());
            this.binding = binding;
        }
    }

}

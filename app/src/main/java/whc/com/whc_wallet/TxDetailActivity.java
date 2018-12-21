package whc.com.whc_wallet;

/**
 * Created by chuanbei.qiao on 2018/11/13.
 */

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonElement;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import whc.com.whc_wallet.util.Utils;
import wormhole.net.Retrofit;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.HistrotyRes;

public class TxDetailActivity extends BaseActivity {
    public static final String EXTRA_TX_HASH = "extra_tx_hash";
    private ListView listView;
    private JsonElement txDeatail;
    private ArrayList<String> keys = new ArrayList<>();
    private MyAdapter mAdapter = new MyAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listfunding);
        listView = (ListView) findViewById(R.id.listView1);
        setTilte(R.string.tx_detail);
        String txHash = getIntent().getStringExtra(EXTRA_TX_HASH);

        Call<BaseResponse<JsonElement>> call =  Retrofit.getRetrofit().getService().fetchTxDetail(txHash);
        call.enqueue(new Callback<BaseResponse<JsonElement>>() {
            @Override
            public void onResponse(Call<BaseResponse<JsonElement>> call, Response<BaseResponse<JsonElement>> response) {
                if (!Utils.dealCommonNetRes(response)) return;
                txDeatail = response.body().result;
                keys.addAll(txDeatail.getAsJsonObject().keySet());
                keys.remove("recipients");
                keys.remove("subsends");
                removeEmptyKeyValue();
                listView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<BaseResponse<JsonElement>> call, Throwable t) {

            }
        });


    }

    private void removeEmptyKeyValue() {
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            JsonElement valueJe = txDeatail.getAsJsonObject().get(key);
            if (null == valueJe) {
                it.remove();
                continue;
            }
            if (TextUtils.isEmpty(valueJe.toString())) {
                it.remove();
                continue;
            }
        }
    }


    //自定义适配器
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return keys.size();
        }
        @Override
        public Object getItem(int position) {
            String key = keys.get(position);
            return txDeatail.getAsJsonObject().get(key).toString();
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
//            LogUtils.d("position=" + position);
//            LogUtils.d(convertView);
//            LogUtils.d("------------------------");
            ViewHolder vh = new ViewHolder();
            //通过下面的条件判断语句，来循环利用。如果convertView = null ，表示屏幕上没有可以被重复利用的对象。
            if(convertView==null){
                //创建View
                convertView = getLayoutInflater().inflate(R.layout.tx_detail_item, null);
                vh.keyV = (TextView) convertView.findViewById(R.id.keyV);
                vh.valueV = (TextView) convertView.findViewById(R.id.valueV);
                vh.valueV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = ((TextView)v).getText().toString();
                        ClipData myClip = ClipData.newPlainText("text", text);
                        ClipboardManager myClipboard =
                                (ClipboardManager) v.getContext().getSystemService(CLIPBOARD_SERVICE);
                        myClipboard.setPrimaryClip(myClip);
                        ToastUtils.showLong(R.string.copy_already);
                    }
                });
                convertView.setTag(vh);
            }else{
                vh = (ViewHolder)convertView.getTag();
            }
            HistrotyRes crowdFunding = null;
            String key = keys.get(position);
            vh.keyV.setText(key);
            String val = txDeatail.getAsJsonObject().get(key).toString();
            if (null != val && val.startsWith("\"") && val.endsWith("\"")) {
                val = val.substring(1, val.length() - 1);
            }
            vh.valueV.setText(val);
            return convertView;
        }

    }

    static class ViewHolder{
        TextView keyV;
        TextView valueV;
    }
}

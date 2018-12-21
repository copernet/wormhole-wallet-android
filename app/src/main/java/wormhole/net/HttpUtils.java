package wormhole.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by chuanbei.qiao on 2018/8/29.
 */
public class HttpUtils {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

    public <T> void getdata(String url, final NetDataCallBack<T> netDataCallback, final Class<T> tclass) {
        //构造一个Request对象
        Request request = new Request.Builder().url(url).build();
        //通过request的对象去构造得到一个Call对象
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                netDataCallback.faild(499, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();

                Gson gson = new Gson();
                final T t = gson.fromJson(string, tclass);
                netDataCallback.success(t);
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        netDataCallback.success(t);
//                    }
//                });

            }
        });

    }

    //post请求
    public <T> void getLoadDataPost(String url, final NetDataCallBack netDataCallback, final Class<T> tClass, RequestBody body) {
        final Request request = new Request.Builder().url(url).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                netDataCallback.faild(499, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final T t = new Gson().fromJson(response.body().string(), tClass);
                netDataCallback.success(t);
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        netDataCallback.success(t);
//
//                    }
//                });
            }
        });
    }
}
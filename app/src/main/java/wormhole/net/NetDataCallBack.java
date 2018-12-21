package wormhole.net;

/**
 * Created by chuanbei.qiao on 2018/8/29.
 */

public interface NetDataCallBack<T> {
    void success(T t);
    void faild(int positon, String str);
}

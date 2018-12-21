package wormhole.net.response;

/**
 * Created by chuanbei.qiao on 2018/8/29.
 */

public class BaseResponse<T> {
    public int code;
    public String message;
    public T result;

}

package whc.com.whc_wallet.util;

/**
 * Created by chuanbei.qiao on 2018/11/3.
 */

public class MessageEvent {
    public static final int CHANGE_WALLET = 2;
    private int msgType;
    public MessageEvent(int msgType) {
        this.msgType = msgType;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }
}

package wormhole.net.response;

import java.math.BigDecimal;

/**
 * Created by chuanbei.qiao on 2018/9/6.
 */

public class Balance {
    public boolean showBalance;
    public String address;
    public String property_name;
    public int property_id;
    public int precision;
    public int pendingpos;
    public int pendingneg;
    public BigDecimal balance_available = new BigDecimal(0);
}

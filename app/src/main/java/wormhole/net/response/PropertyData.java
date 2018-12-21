package wormhole.net.response;

/**
 * Created by chuanbei.qiao on 2018/9/7.
 */

public class PropertyData {
    public static final int FIXED_FUND = 1;
    public static final int MANAGABLE_FUND = 2;
    public static final int CROWD_FUND = 3;

    public boolean active;
    public boolean closedearly;
    public boolean fixedissuance;
    public boolean freezingenabled;
    public boolean managedissuance;

    public String addedissuertokens;
    public String amountraised;
    public String category;
    public String closetx;
    public String creationtxid;
    public String data;
    public String issuer;
    public String name;
    public String participanttransactions;
    public String precision;
    public String subcategory;
    public String tokensissued;
    public String tokensperunit;
    public String totaltokens;
    public String url;

    public long deadline;
    public float earlybonus;
    public long propertyid;
    public double purchasedtokens;
    public long propertyiddesired;
    public long starttime;

    public int getPropertyType() {
        if (fixedissuance) {
            return FIXED_FUND;
        } else if (managedissuance) {
            return MANAGABLE_FUND;
        } else {
            return CROWD_FUND;
        }
    }
}

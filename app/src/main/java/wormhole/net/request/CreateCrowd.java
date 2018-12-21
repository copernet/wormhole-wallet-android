package wormhole.net.request;

import android.databinding.BaseObservable;

/**
 * Created by chuanbei.qiao on 2018/10/25.
 */

public class CreateCrowd extends BaseObservable {
    public String transaction_version = "0";
    public float fee;
    public String transaction_from;
    public int ecosystem = 1;
    public int precision;
    public int previous_property_id;
    public String property_category;
    public String property_subcategory;
    public String property_name;
    public String property_url;
    public String property_data;
    public String number_properties;
    public int currency_identifier_desired = 1;
    public long deadline;
    public String earlybird_bonus;
    public String total_number;



}

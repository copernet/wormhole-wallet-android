package wormhole.net;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import wormhole.net.response.Balance;
import wormhole.net.response.BasePageResponse;
import wormhole.net.response.BaseResponse;
import wormhole.net.response.CheckUpdate;
import wormhole.net.response.CommonResult;
import wormhole.net.response.FundingTxData;
import wormhole.net.response.GetFee;
import wormhole.net.response.HistrotyRes;
import wormhole.net.response.ListbyownerRes;
import wormhole.net.response.Notice;
import wormhole.net.response.OwnersData;
import wormhole.net.response.PropertyData;

/**
 * Created by chuanbei.qiao on 2018/8/29.
 */


public interface IBeanService {
    @FormUrlEncoded
    @POST("property/listbyowner")
    Call<BaseResponse<BasePageResponse<ListbyownerRes>>> listByOwner(@Field("address") String[] addrs);

    @FormUrlEncoded
    @POST("balance/addresses")
    Call<BaseResponse<Map<String, ArrayList<Balance>>>> getBalance(@Field("address") String[] addrs);

    @POST("transaction/fee")
    Call<BaseResponse<GetFee>> getFee();

    @FormUrlEncoded
    @POST("getunsigned/68")
    Call<BaseResponse<CommonResult>> burnBCH(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("history/list")
    Call<BaseResponse<BasePageResponse<HistrotyRes>>> historyList(@FieldMap Map<String, Object> map, @Query("pageSize") int pageSize, @Query("pageNo") int pageNo);

    @FormUrlEncoded
    @POST("notify/")
    Call<BaseResponse<BasePageResponse<Notice>>> getNotice(@FieldMap Map<String, Object> map);

    @GET("/device/update/android")
    Call<BaseResponse<CheckUpdate>> checkUpdate();

    @FormUrlEncoded
    @POST("transaction/push")
    Call<JsonElement> broadcast(@Field("signedTx") String signedTx);

    @FormUrlEncoded
    @POST("getunsigned/51")
    Call<BaseResponse<CommonResult>> createCrowdFunding(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("getunsigned/54")
    Call<BaseResponse<CommonResult>> createManagableFunding(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("getunsigned/50")
    Call<BaseResponse<CommonResult>> createFixedFunding(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("getunsigned/1")
    Call<BaseResponse<CommonResult>> joinFunding(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("getunsigned/55")
    Call<BaseResponse<CommonResult>> seo(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("getunsigned/0")
    Call<BaseResponse<CommonResult>> translate(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("getunsigned/3")
    Call<BaseResponse<CommonResult>> airDrop(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("getunsigned/4")
    Call<BaseResponse<CommonResult>> sendAll(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("getunsigned/56")
    Call<BaseResponse<CommonResult>> burnTokens(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("getunsigned/70")
    Call<BaseResponse<CommonResult>> changeOwner(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("getunsigned/53")
    Call<BaseResponse<CommonResult>> closeCrowdFunding(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("history/id/{id}")
    Call<BaseResponse<FundingTxData>> txOfFunding(@Path(value="id", encoded=true) long id, @Field("start") int start, @Field("count") int count);

    @FormUrlEncoded
    @POST("property/listowners/{id}")
    Call<BaseResponse<BasePageResponse<OwnersData>>> ownersOfFunding(@Path(value="id", encoded=true) long id, @Query("pageSize") int pageSize, @Query("pageNo") int pageNo, @Field("pageNo") int pageNoF);

    @GET("category")
    Call<BaseResponse<Map<String, ArrayList<String>>>> fetchCategory();

    @GET("history/detail")
    Call<BaseResponse<JsonElement>> fetchTxDetail(@Query(value="tx_hash", encoded=true) String hash);

    @GET("crowdsale/list/active")
    Call<BaseResponse<BasePageResponse<PropertyData>>> fetchActiveCrowd(@QueryMap Map<String, Object> fieldMap);

    @GET("property/list")
    Call<BaseResponse<BasePageResponse<PropertyData>>> fetchAllProoerties(@QueryMap Map<String, Object> fieldMap);
}
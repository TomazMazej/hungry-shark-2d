package com.feri.hungryshark2d.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BlockchainApi {

    @FormUrlEncoded
    @POST("receive")
    Call<PostRequest> createCoinPost(
            @Field("id") String id,
            @Field("skin") String skin,
            @Field("amount") int coins);

    @FormUrlEncoded
    @POST("buy")
    Call<PostRequest> createSkinPost(
            @Field("id") String id,
            @Field("skin") String skin,
            @Field("amount") int coins);

    @GET("users/coins/{id}")
    Call<GetCoinsRequest> getCoinsPost(@Path("id") String id);

    @GET("users/skins/{id}")
    Call<GetSkinsRequest> getSkinsPost(@Path("id") String id);
}

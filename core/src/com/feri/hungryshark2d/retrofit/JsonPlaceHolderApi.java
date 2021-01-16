package com.feri.hungryshark2d.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

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
    //isto get pa post še za skine!
    //2 nova razreda za get in post pa poimenuj tak da se bo vedl
    //isto funkcije v GameManager in potem kličeš get v shopu
    //v shopu treba dodat if stavke če smo ze kupl da removamo ceno in pa omejimo da nemores zbrat sharka ko ga se nisi kupo
}

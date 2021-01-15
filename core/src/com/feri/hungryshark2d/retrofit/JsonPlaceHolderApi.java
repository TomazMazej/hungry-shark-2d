package com.feri.hungryshark2d.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @FormUrlEncoded
    @POST("receive")
    Call<PostRequest> createPost(
            @Field("id") String id,
            @Field("skin") String skin,
            @Field("amount") int coins);

    @GET("users/{id}")
    Call<GetRequest> getPost(@Path("id") String id);

    //isto get pa post še za skine!
    //2 nova razreda za get in post pa poimenuj tak da se bo vedl
    //isto funkcije v GameManager in potem kličeš get v shopu
    //v shopu treba dodat if stavke če smo ze kupl da removamo ceno in pa omejimo da nemores zbrat sharka ko ga se nisi kupo
}

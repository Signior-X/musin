package com.example.musin.data;

import com.example.musin.data.model.Post;
import com.example.musin.data.model.PostRequest;
import com.example.musin.data.model.PostUrlYt;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @POST("/api/getdata")
    @Headers({"Content-Type: application/json"})
    // Taking gson values in Call<class> from the request which we do
    Call<Post> savePost(@Body PostRequest body);  // Give the JSON Body to pass in the call

    @POST("/api/getytlink")
    @Headers({"Content-Type: application/json"})
    // Taking gson values in Call<class> from the request which we do
    Call<Post> saveUrl(@Body PostUrlYt yturl);

    @GET("/api/check")
    Call<ResponseBody> firstGet();

}

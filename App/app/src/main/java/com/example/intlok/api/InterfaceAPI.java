package com.example.intlok.api;

import com.example.intlok.models.Follower;
import com.example.intlok.models.FollowerResponse;
import com.example.intlok.models.LoginRequest;
import com.example.intlok.models.LoginResponse;
import com.example.intlok.models.Post;
import com.example.intlok.models.RegisterAccountRequest;
import com.example.intlok.models.RegisterAccountResponse;
import com.example.intlok.models.RegisterUserRequest;
import com.example.intlok.models.RegisterUserResponse;
import com.example.intlok.models.Cuenta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InterfaceAPI {

    @POST("login")
    Call<LoginResponse> userlogin(@Body LoginRequest loginRequest);

    @POST("register")
    Call<RegisterUserResponse> createUser(@Body RegisterUserRequest registerUserRequest);

    @POST("register/cuenta")
    Call<RegisterAccountResponse> createAccount(@Body RegisterAccountRequest registerAccountRequest, @Header("Authorization") String token);

    @GET("getSeguidores")
    Call<Integer> getFollowers(@Header("Authorization") String token);

    @GET("getSeguidos")
    Call<Integer> getSeguidos(@Header("Authorization") String token);

    @GET("getPostsCount")
    Call<Integer> getPostsCount(@Header("Authorization") String token);

    @GET("posts")
    Call<List<Post>> getPosts(@Header("Authorization") String token);

    @GET("getCuenta")
    Call<Cuenta> getCuenta(@Header("Authorization") String token);

    @GET("getImagenesPosts")
    Call<ImagenPostResponse> getImagenesPost(int idMultimedia,@Header("Authorization") String token);

    @GET("getFotoPerfil")
    Call<MultimediaResponse> getImagenPerfil(@Header("Authorization") String token);


}

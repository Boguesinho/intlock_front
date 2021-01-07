package com.example.intlok.api;

import android.graphics.Bitmap;
import android.media.Image;

import com.example.intlok.models.Follower;
import com.example.intlok.models.FollowerResponse;
import com.example.intlok.models.ImagenPerfilRequest;
import com.example.intlok.models.ImagenPostResponse;
import com.example.intlok.models.LoginRequest;
import com.example.intlok.models.LoginResponse;
import com.example.intlok.models.MultimediaResponse;
import com.example.intlok.models.Post;
import com.example.intlok.models.RegisterAccountRequest;
import com.example.intlok.models.RegisterAccountResponse;
import com.example.intlok.models.RegisterUserRequest;
import com.example.intlok.models.RegisterUserResponse;
import com.example.intlok.models.Cuenta;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @GET("getImagenPost")
    Call<ImagenPostResponse> getImagenesPost(int idMultimedia,@Header("Authorization") String token);

    @GET("getFotoPerfil")
    Call<MultimediaResponse> getImagenPerfil(@Header("Authorization") String token);

    @POST("subirFotoPerfil")
    @Multipart
    Call<ResponseBody> subirFotoPerfil(@Header("Authorization") String token, @Part MultipartBody.Part file);


}

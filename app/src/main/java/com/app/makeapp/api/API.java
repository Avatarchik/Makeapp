package com.app.makeapp.api;

import com.app.makeapp.api.models.CategoriesModel;
import com.app.makeapp.api.models.InformationModel;
import com.app.makeapp.api.models.SignUpModel;
import com.app.makeapp.api.models.VideosModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by gleb on 19.12.16.
 */

public interface API {

    @Multipart
    @POST("user/auth")
    Call<SignUpModel> signIn(@Part("email") String email,
                              @Part("password") String pass);

    @Multipart
    @POST("user/auth")
    Call<SignUpModel> socialSignIn(@Part("email") String email,
                                   @Part("first_name") String firstName,
                                   @Part("last_name") String lastName,
                                   @Part("id") String id,
                                   @Part("service") String service);

    @Multipart
    @POST("user/sign-up")
    Call<SignUpModel> signUp(@Part("email") String email,
                              @Part("password") String pass,
                              @Part("first_name") String firstName,
                              @Part("last_name") String lastName);

    @Multipart
    @POST("user/request-password-reset")
    Call<ResponseBody> forgotPass(@Part("email") String email);

    @Multipart
    @POST("object/add-value")
    Call<ResponseBody> addPurchase(@Part("object") int user_object_id,
                                   @Part("value") int category_id,
                                    @Part("type") String type);

    @Multipart
    @POST("user/update-password")
    Call<ResponseBody> resetPassword(@Part("password_old") String passwordOld,
                                     @Part("password_new") String passwordNew,
                                     @Part("password_repeat") String passwordRepeat);

    @GET("object/index?type=videocats&?alias=videos")
    Call<List<CategoriesModel>> getCategoryList(@Query("value") int id);

    @GET("object/get-by-value?alias=category")
    Call<List<VideosModel>> getVideosById(@Query("value") int id);

    @GET("custom/get-news")
    Call<InformationModel> getInformation();
}

package com.example.de_tai_di_dong.getData;

import com.example.de_tai_di_dong.model.ResultLogin;
import com.example.de_tai_di_dong.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataUser {

    @GET("login")
    Call<ResultLogin>getLogin(
            @Query("username") String username,
            @Query("password") String pass
    );

    @GET("user/{id}")
    Call<ArrayList<User>> getInfoUser(
            @Path("id") int id
    );
}

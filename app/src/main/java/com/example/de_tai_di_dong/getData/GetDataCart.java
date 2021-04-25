package com.example.de_tai_di_dong.getData;

import com.example.de_tai_di_dong.model.Cart;
import com.example.de_tai_di_dong.model.CartItem;
import com.example.de_tai_di_dong.model.ResultCartItem;
import com.example.de_tai_di_dong.model.ResultLogin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataCart {
    @GET("checkOut/{id}")
    Call<ResultLogin> putCheckOut(
            @Path("id") int id,
            @Query("name")String name,
            @Query("phone") String phone,
            @Query("adress")String  address
    );

//    @GET("cartCount")
//    Call<ArrayList<Cart>> getCartCount(
//            @Query("userId") int userId
//    );

    @POST("Cart")
    Call<ResultLogin> getCartItem(
            @Body CartItem cartItem
    );


    @DELETE("Cart")
    Call<ResultLogin> deleteCartItem(
            @Query("userId") int userId,
            @Query("proId") int productId
    );
}



package com.example.de_tai_di_dong.getData;

import com.example.de_tai_di_dong.model.Product;
import com.example.de_tai_di_dong.model.ResultLogin;
import com.example.de_tai_di_dong.model.SanPham;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetData_Product {

    @GET("Product")
    Call<ArrayList<SanPham>> getAllSanPham() ;

    @GET("topten")
    Call<ArrayList<SanPham>> getStatisSanPham() ;

    @GET("OrderDetail")
    Call<ArrayList<SanPham>> getDetailOrder(
            @Query("orderId") int orderId
    ) ;

    @GET("Cart/{id}")
    Call<ArrayList<SanPham>> getProductCart(
        @Path("id") int id
    );

    @GET("allProsOfProGroup")
    Call<List<SanPham>> getProductGroup(
            @Query("proGroupID") int idProductGroup
    );

    @GET("allSalePros")
    Call<List<SanPham>> getAllProductSale() ;

    @GET("product/{id}")
    Call<ArrayList<SanPham>> getProduct(
            @Path("id") int id
    );
    @GET("product/{id}")
    Call<ArrayList<Product>> getProductNew(
            @Path("id") int id
    );


    @DELETE("product/{id}")
    Call<ResultLogin> deleteProduct(
            @Path("id") int id
    );
    @POST("Product")
    Call<ResultLogin> addProduct(
            @Body Product sp
            );
    @PUT("Product")
    Call<ResultLogin> putProduct(
            @Body Product sp
    );
}

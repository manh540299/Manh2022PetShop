package com.manh.petshopdemo1.api;

import com.manh.petshopdemo1.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {

    ApiService apiService=new Retrofit.Builder()
            .baseUrl("https://demo9762540.mockable.io/")
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.class);
    @GET("m_petshop_product")
    Call<List<Product>> getProduct();
}

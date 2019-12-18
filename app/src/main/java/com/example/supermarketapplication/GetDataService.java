package com.example.supermarketapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {


        //@GET("/v2/5dcfef932f000080003f1f34")
        @GET("/v2/5df3a80b3100006c00b58612")
        Call<List<RetrofitData>> getAllData();


}

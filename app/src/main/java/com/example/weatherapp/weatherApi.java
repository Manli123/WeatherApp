package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weatherApi {
    @GET("weather")
    Call<Users> getweather(@Query("q") String cityname,@Query("appid") String appkey);
}

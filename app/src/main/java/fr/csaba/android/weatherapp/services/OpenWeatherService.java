package fr.csaba.android.weatherapp.services;

import fr.csaba.android.weatherapp.models.City;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {
    @GET("weather")
    Call<City> getWeather(@Query("lat") double lat, @Query("lon") double lon, @Query("units") String units, @Query("lang") String lang, @Query("appid") String appid);

    @GET("weather")
    Call<City> getWeather(@Query("q") String city, @Query("units") String units, @Query("lang") String lang, @Query("appid") String appid);
}

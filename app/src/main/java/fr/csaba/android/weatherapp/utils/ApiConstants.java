package fr.csaba.android.weatherapp.utils;

import fr.csaba.android.weatherapp.services.OpenWeatherService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConstants {
    public static final String APPID = "01897e497239c8aff78d9b8538fb24ea";
    public static final String UNITS = "metric";
    public static final String LANG = "fr";
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    public static final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    public static final OpenWeatherService service = retrofit.create(OpenWeatherService.class);
}

package fr.csaba.android.weatherapp.utils;

import androidx.annotation.NonNull;


import fr.csaba.android.weatherapp.interfaces.UpdateUiFunction;
import fr.csaba.android.weatherapp.models.CityGson;
import fr.csaba.android.weatherapp.services.OpenWeatherService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final String APPID = "01897e497239c8aff78d9b8538fb24ea";
    public static final String UNITS = "metric";
    public static final String LANG = "fr";
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    public static final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    public static final OpenWeatherService service = retrofit.create(OpenWeatherService.class);

    public static void callApi(retrofit2.Call<CityGson> call, UpdateUiFunction<retrofit2.Response<CityGson>> updateUiFunction) {
        call.enqueue(new Callback<CityGson>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<CityGson> call, @NonNull Response<CityGson> response) {
                updateUiFunction.apply(response);
            }

            @Override
            public void onFailure(@NonNull Call<CityGson> call, @NonNull Throwable t) {

            }
        });
    }
}

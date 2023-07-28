package fr.csaba.android.weatherapp.utils;

import androidx.annotation.NonNull;


import fr.csaba.android.weatherapp.interfaces.UpdateUiFunction;
import fr.csaba.android.weatherapp.models.CityGson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Api {
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

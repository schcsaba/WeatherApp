package fr.csaba.android.weatherapp.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;

import java.io.IOException;

import fr.csaba.android.weatherapp.interfaces.UpdateUiFunction;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Api {
    public static void getApiResponse(Request request, UpdateUiFunction<String> updateUiFunction) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    final String stringJson = response.body().string();
                    try {
                        updateUiFunction.apply(stringJson);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    Log.d("TAG", stringJson);
                }
            }
        });
    }
}
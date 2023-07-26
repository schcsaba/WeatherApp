package fr.csaba.android.weatherapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;

import fr.csaba.android.weatherapp.R;
import fr.csaba.android.weatherapp.databinding.ActivityMainBinding;
import fr.csaba.android.weatherapp.models.City;
import fr.csaba.android.weatherapp.utils.Api;
import fr.csaba.android.weatherapp.utils.Util;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private City mCurrentCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textViewCityName.setText(R.string.city_name);
        binding.textViewNoInternet.setText(R.string.no_internet_connection);

        if (Util.isActiveNetwork(this)) {
            Log.d("TAG", "Oui je suis connecté");
            binding.textViewNoInternet.setVisibility(View.INVISIBLE);
            Request request = new Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?lat=47.390026&lon=0.688891&appid=01897e497239c8aff78d9b8538fb24ea&units=metric&lang=fr").build();
            Api.getApiResponse(request, this::updateUI);
        } else {
            Log.d("TAG", "Non j'ai rien du tout");
            binding.linearLayoutHead.setVisibility(View.INVISIBLE);
            binding.buttonFavorites.setVisibility(View.INVISIBLE);
            binding.textViewNoInternet.setVisibility(View.VISIBLE);
        }
        Log.d("TAG", "MainActivity: onCreate()");
    }

    private void updateUI(String stringJson) throws JSONException {
        mCurrentCity = new City(stringJson);
        runOnUiThread(() -> {
            binding.textViewCityName.setText(mCurrentCity.mName);
            binding.textViewDescription.setText(mCurrentCity.mDescription);
            binding.textViewTemperature.setText(mCurrentCity.mTemperature);
            binding.imageViewWeatherIcon.setImageResource(mCurrentCity.mWeatherResIconWhite);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "MainActivity: onDestroy()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "MainActivity: onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG", "MainActivity: onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "MainActivity: onStop()");
    }

    public void onClickButtonFavorites(View view) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }
}
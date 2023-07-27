package fr.csaba.android.weatherapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import fr.csaba.android.weatherapp.R;
import fr.csaba.android.weatherapp.databinding.ActivityMainBinding;
import fr.csaba.android.weatherapp.models.CityGson;
import fr.csaba.android.weatherapp.utils.Api;
import fr.csaba.android.weatherapp.utils.Util;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private CityGson mCurrentCity;

    private static final int REQUEST_CODE = 123;

    private LocationManager mLocationManager;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            Log.d("TAG", "" + lat);
            Log.d("TAG", "" + lon);
            Request request = new Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=01897e497239c8aff78d9b8538fb24ea&units=metric&lang=fr").build();
            Api.getApiResponse(request, MainActivity.this::updateUI, MainActivity.this::updateUi404);
            mLocationManager.removeUpdates(mLocationListener);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textViewNoInternet.setText(R.string.no_internet_connection);

        if (Util.isActiveNetwork(this)) {
            Log.d("TAG", "Oui je suis connecté");
            binding.textViewNoInternet.setVisibility(View.INVISIBLE);
            startLocation();
        } else {
            Log.d("TAG", "Non j'ai rien du tout");
            binding.linearLayoutHead.setVisibility(View.INVISIBLE);
            binding.buttonFavorites.setVisibility(View.INVISIBLE);
            binding.textViewNoInternet.setVisibility(View.VISIBLE);
        }
        Log.d("TAG", "MainActivity: onCreate()");
    }

    private void startLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        } else {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        }
    }

    private void updateUI(String stringJson) {
        Gson gson = new Gson();
        mCurrentCity = gson.fromJson(stringJson, CityGson.class);
        runOnUiThread(() -> {
            binding.textViewCityName.setText(mCurrentCity.getName());
            binding.textViewDescription.setText(mCurrentCity.getWeather().get(0).getDescription());
            binding.textViewTemperature.setText(String.format("%.0f", mCurrentCity.getMain().getTemp()) + " ℃");
            binding.imageViewWeatherIcon.setImageResource(Util.setWeatherIcon(mCurrentCity.getWeather().get(0).getId(), mCurrentCity.getSys().getSunrise() * 1000, mCurrentCity.getSys().getSunset() * 1000));
        });
    }

    private void updateUi404() {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), this.getText(R.string.city_not_found), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("TAG", "Permission granted");
            } else {
                Log.d("TAG", "Permission not granted");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
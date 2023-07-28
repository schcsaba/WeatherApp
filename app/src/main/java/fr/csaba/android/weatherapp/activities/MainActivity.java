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

import fr.csaba.android.weatherapp.R;
import fr.csaba.android.weatherapp.databinding.ActivityMainBinding;
import fr.csaba.android.weatherapp.models.CityGson;
import fr.csaba.android.weatherapp.utils.Api;
import fr.csaba.android.weatherapp.utils.ApiConstants;
import fr.csaba.android.weatherapp.utils.Util;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 123;
    private ActivityMainBinding binding;
    private LocationManager mLocationManager;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            Log.d("TAG", "" + lat);
            Log.d("TAG", "" + lon);
            Call<CityGson> call = ApiConstants.service.getWeather(lat, lon, ApiConstants.UNITS, ApiConstants.LANG, ApiConstants.APPID);
            Api.callApi(call, MainActivity.this::updateUi);
            mLocationManager.removeUpdates(mLocationListener);
        }
    };

    private void updateUi(@NonNull Response<CityGson> response) {
        if (response.isSuccessful()) {
            binding.progressCircular.setVisibility(View.INVISIBLE);
            assert response.body() != null;
            binding.textViewCityName.setText(response.body().getName());
            binding.textViewDescription.setText(response.body().getWeather().get(0).getDescription());
            binding.textViewTemperature.setText(String.format("%.0f", response.body().getMain().getTemp()) + " ℃");
            binding.imageViewWeatherIcon.setImageResource(Util.setWeatherIcon(response.body().getWeather().get(0).getId(), response.body().getSys().getSunrise() * 1000, response.body().getSys().getSunset() * 1000));
        } else {
            Toast.makeText(getApplicationContext(), this.getText(R.string.city_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.progressCircular.setVisibility(View.INVISIBLE);
        binding.textViewErrorMessage.setVisibility(View.INVISIBLE);

        if (Util.isActiveNetwork(this)) {
            Log.d("TAG", "Oui je suis connecté");
            binding.progressCircular.setVisibility(View.VISIBLE);
            startLocation();
        } else {
            Log.d("TAG", "Non j'ai rien du tout");
            binding.linearLayoutHead.setVisibility(View.INVISIBLE);
            binding.buttonFavorites.setVisibility(View.INVISIBLE);
            binding.textViewErrorMessage.setText(R.string.no_internet_connection);
            binding.textViewErrorMessage.setVisibility(View.VISIBLE);
        }
        Log.d("TAG", "MainActivity: onCreate()");
    }

    private void startLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        } else {
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, mLocationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("TAG", "Permission granted");
                startLocation();
            } else {
                Log.d("TAG", "Permission not granted");
                binding.progressCircular.setVisibility(View.INVISIBLE);
                binding.textViewErrorMessage.setText(R.string.location_not_permitted);
                binding.textViewErrorMessage.setVisibility(View.VISIBLE);
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
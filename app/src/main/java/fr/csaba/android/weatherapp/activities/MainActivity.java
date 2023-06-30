package fr.csaba.android.weatherapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import fr.csaba.android.weatherapp.Keys;
import fr.csaba.android.weatherapp.R;
import fr.csaba.android.weatherapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textViewCityName.setText(R.string.city_name);
        binding.textViewNoInternet.setText(R.string.no_internet_connection);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("TAG", "Oui je suis connecté");
            binding.textViewNoInternet.setVisibility(View.INVISIBLE);
        } else {
            Log.d("TAG", "Non j'ai rien du tout");
            binding.linearLayoutHead.setVisibility(View.INVISIBLE);
            binding.buttonFavorites.setVisibility(View.INVISIBLE);
            binding.textViewNoInternet.setVisibility(View.VISIBLE);
        }
        Log.d("TAG", "MainActivity: onCreate()");
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
        String message = binding.editTextMessage.getText().toString();
        Intent intent = new Intent(this, FavoriteActivity.class);
        intent.putExtra(Keys.MESSAGE_KEY, message);
        startActivity(intent);
    }
}
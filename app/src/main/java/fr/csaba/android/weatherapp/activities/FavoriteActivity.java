package fr.csaba.android.weatherapp.activities;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;

import java.util.ArrayList;

import fr.csaba.android.weatherapp.R;
import fr.csaba.android.weatherapp.adapters.FavoriteAdapter;
import fr.csaba.android.weatherapp.databinding.ActivityFavoriteBinding;
import fr.csaba.android.weatherapp.models.City;
import fr.csaba.android.weatherapp.utils.Api;
import fr.csaba.android.weatherapp.utils.Util;
import okhttp3.Request;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;
    private ArrayList<City> mCities;
    private FavoriteAdapter mFavoriteAdapter;
    private City mNewCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCities = Util.initFavoriteCities(this);

        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add a city");
            builder.setMessage("Here you can add a city");
            View v = LayoutInflater.from(this).inflate(R.layout.dialog_add_favorite, null);
            final EditText editTextCity = v.findViewById(R.id.edit_text_dialog_city);
            DialogInterface.OnClickListener onClickListenerPositive = (dialogInterface, i) -> {
                String cityName = editTextCity.getText().toString();
                Request request = new Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=01897e497239c8aff78d9b8538fb24ea&units=metric&lang=fr").build();
                Api.getApiResponse(request, this::updateUI);
            };
            builder.setPositiveButton(android.R.string.ok, onClickListenerPositive);
            builder.setNegativeButton(android.R.string.cancel, null);

            builder.setView(v);
            builder.create().show();
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.include.recyclerViewFavorites.setLayoutManager(layoutManager);
        mFavoriteAdapter = new FavoriteAdapter(this, mCities);
        binding.include.recyclerViewFavorites.setAdapter(mFavoriteAdapter);

        Log.d("TAG", "FavoriteActivity: onCreate()");
    }

    private void updateUI(String stringJson) throws JSONException {
        mNewCity = new City(stringJson);
        mCities.add(mNewCity);
        Util.saveFavoriteCities(this, mCities);
        runOnUiThread(() -> mFavoriteAdapter.notifyItemInserted(mCities.size() - 1));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "FavoriteActivity: onDestroy()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "FavoriteActivity: onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG", "FavoriteActivity: onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "FavoriteActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "FavoriteActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "FavoriteActivity: onStop()");
    }
}
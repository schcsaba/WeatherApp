package fr.csaba.android.weatherapp.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import fr.csaba.android.weatherapp.R;
import fr.csaba.android.weatherapp.adapters.FavoriteAdapter;
import fr.csaba.android.weatherapp.databinding.ActivityFavoriteBinding;
import fr.csaba.android.weatherapp.models.City;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;
    private ArrayList<City> mCities;
    private FavoriteAdapter mFavoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCities = new ArrayList<>();
        City city1 = new City("Montréal", "Légères pluies", "22°C", R.drawable.weather_rainy_grey);
        City city2 = new City("New York", "Ensoleillé", "22°C", R.drawable.weather_sunny_grey);
        City city3 = new City("Paris", "Nuageux", "24°C", R.drawable.weather_foggy_grey);
        City city4 = new City("Toulouse", "Pluies modérées", "20°C", R.drawable.weather_rainy_grey);
        mCities.add(city1);
        mCities.add(city2);
        mCities.add(city3);
        mCities.add(city4);

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
            @SuppressLint("NotifyDataSetChanged") DialogInterface.OnClickListener onClickListenerPositive = (dialogInterface, i) -> {
                String cityName = editTextCity.getText().toString();
                mCities.add(new City(cityName, "Nuageux", "24°C", R.drawable.weather_foggy_grey));
                mFavoriteAdapter.notifyDataSetChanged();
            };
            builder.setPositiveButton("OK",  onClickListenerPositive);
            DialogInterface.OnClickListener onClickListenerNegative = (dialogInterface, i) -> {
                builder.create().cancel();
            };
            builder.setNegativeButton("Cancel", onClickListenerNegative);

            builder.setView(v);
            builder.create().show();
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.include.recyclerViewFavorites.setLayoutManager(layoutManager);
        mFavoriteAdapter = new FavoriteAdapter(this, mCities);
        binding.include.recyclerViewFavorites.setAdapter(mFavoriteAdapter);

        Log.d("TAG", "FavoriteActivity: onCreate()");
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
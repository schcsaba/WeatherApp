package fr.csaba.android.weatherapp.activities;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import fr.csaba.android.weatherapp.R;
import fr.csaba.android.weatherapp.adapters.FavoriteAdapter;
import fr.csaba.android.weatherapp.databinding.ActivityFavoriteBinding;
import fr.csaba.android.weatherapp.models.City;
import fr.csaba.android.weatherapp.utils.Api;
import fr.csaba.android.weatherapp.utils.Util;
import retrofit2.Call;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;
    private ArrayList<City> mCities;
    private FavoriteAdapter mFavoriteAdapter;

    private void updateUI(Response<City> response) {
        if (response.isSuccessful()) {
            mCities.add(response.body());
            Util.saveFavoriteCities(this, mCities);
            mFavoriteAdapter.notifyItemInserted(mCities.size() - 1);
        } else {
            Toast.makeText(getApplicationContext(), this.getText(R.string.city_not_found), Toast.LENGTH_SHORT).show();
        }
    }

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
                Call<City> call = Api.service.getWeather(cityName, Api.UNITS, Locale.getDefault().getLanguage(), Api.APPID);
                Api.callApi(call, this::updateUI);
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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                City city = mCities.remove(position);
                Util.saveFavoriteCities(FavoriteActivity.this, mCities);
                mFavoriteAdapter.notifyItemRemoved(position);
                Snackbar.make(binding.coordinatorLayoutFavorites, city.getName() + getString(R.string.is_deleted), Snackbar.LENGTH_LONG).setAction(android.R.string.cancel, v -> {
                    mCities.add(city);
                    Util.saveFavoriteCities(FavoriteActivity.this, mCities);
                    mFavoriteAdapter.notifyItemInserted(mCities.size() - 1);
                }).show();
            }
        });
        itemTouchHelper.attachToRecyclerView(binding.include.recyclerViewFavorites);

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
package fr.csaba.android.weatherapp.activities;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
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

import java.io.IOException;
import java.util.ArrayList;

import fr.csaba.android.weatherapp.R;
import fr.csaba.android.weatherapp.adapters.FavoriteAdapter;
import fr.csaba.android.weatherapp.databinding.ActivityFavoriteBinding;
import fr.csaba.android.weatherapp.models.City;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;
    private ArrayList<City> mCities;
    private FavoriteAdapter mFavoriteAdapter;

    private OkHttpClient mOkHttpClient;
    private City mNewCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOkHttpClient = new OkHttpClient();

        mCities = new ArrayList<>();

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
                getCityFromApi(request);
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

    private void getCityFromApi(Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    final String stringJson = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            updateUI(stringJson);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    Log.d("TAG", stringJson);
                }
            }
        });
    }

    private void updateUI(String stringJson) throws JSONException {
        mNewCity = new City(stringJson);
        mCities.add(mNewCity);
        mFavoriteAdapter.notifyItemInserted(mCities.size() - 1);
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
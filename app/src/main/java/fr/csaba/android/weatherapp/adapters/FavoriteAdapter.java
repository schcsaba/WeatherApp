package fr.csaba.android.weatherapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.csaba.android.weatherapp.R;
import fr.csaba.android.weatherapp.activities.MapsActivity;
import fr.csaba.android.weatherapp.models.City;
import fr.csaba.android.weatherapp.utils.Util;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<City> mCities;

    public FavoriteAdapter(Context context, ArrayList<City> cities) {
        mContext = context;
        mCities = cities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_city, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = mCities.get(position);
        holder.mImageViewWeatherIcon.setImageResource(Util.setWeatherIcon(city.getWeather().get(0).getId()));
        holder.mTextViewCityName.setText(city.getName());
        holder.mTextViewDescription.setText(city.getWeather().get(0).getDescription());
        holder.mTextViewTemperature.setText(String.format("%.0f", city.getMain().getTemp()) + " ℃");
        holder.mCity = city;
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        public ImageView mImageViewWeatherIcon;
        public TextView mTextViewCityName;
        public TextView mTextViewDescription;
        public TextView mTextViewTemperature;
        public City mCity;
        public ViewHolder(View view) {
            super(view);
            mImageViewWeatherIcon = view.findViewById(R.id.image_view_weather_icon);
            mTextViewCityName = view.findViewById(R.id.text_view_city_name);
            mTextViewDescription = view.findViewById(R.id.text_view_description);
            mTextViewTemperature = view.findViewById(R.id.text_view_temperature);
            view.setOnLongClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d("TAG", mCity.getName());
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Remove a city");
            builder.setMessage("Do you really want to remove this city? " + mCity.getName());
            @SuppressLint("InflateParams") View vRemove = LayoutInflater.from(mContext).inflate(R.layout.dialog_remove_favorite, null);
            @SuppressLint("NotifyDataSetChanged") DialogInterface.OnClickListener onClickListenerPositive = (dialogInterface, i) -> {
                mCities.remove(mCity);
                Util.saveFavoriteCities(mContext, mCities);
                notifyItemRemoved(getAbsoluteAdapterPosition());
            };
            builder.setPositiveButton(android.R.string.ok,  onClickListenerPositive);
            builder.setNegativeButton(android.R.string.cancel, null);

            builder.setView(vRemove);
            builder.create().show();
            return false;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, MapsActivity.class);
            intent.putExtra("city", mCity);
            mContext.startActivity(intent);
        }
    }
}

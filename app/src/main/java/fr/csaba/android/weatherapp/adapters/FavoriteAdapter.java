package fr.csaba.android.weatherapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import fr.csaba.android.weatherapp.models.City;

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
        holder.mImageViewWeatherIcon.setImageResource(city.mWeatherIcon);
        holder.mTextViewCityName.setText(city.mName);
        holder.mTextViewDescription.setText(city.mDescription);
        holder.mTextViewTemperature.setText(city.mTemperature);
        holder.mCity = city;
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

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
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d("TAG", mCity.mName);
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Remove a city");
            builder.setMessage("Do you really want to remove this city? " + mCity.mName);
            @SuppressLint("InflateParams") View vRemove = LayoutInflater.from(mContext).inflate(R.layout.dialog_remove_favorite, null);
            @SuppressLint("NotifyDataSetChanged") DialogInterface.OnClickListener onClickListenerPositive = (dialogInterface, i) -> {
                mCities.remove(mCity);
                notifyDataSetChanged();
            };
            builder.setPositiveButton("OK",  onClickListenerPositive);
            DialogInterface.OnClickListener onClickListenerNegative = (dialogInterface, i) -> {
                builder.create().cancel();
            };
            builder.setNegativeButton("Cancel", onClickListenerNegative);

            builder.setView(vRemove);
            builder.create().show();
            return false;
        }
    }
}

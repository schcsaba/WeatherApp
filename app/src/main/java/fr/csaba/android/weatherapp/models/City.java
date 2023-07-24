package fr.csaba.android.weatherapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class City {
    public String mName;
    public String mDescription;
    public String mTemperature;
    public int mWeatherIcon;
    public int mIdCity;
    public double mLatitude;
    public double mLongitude;
    public int mWeatherId;
    public int mSunrise;
    public int mSunset;

    public City(String stringJson) throws JSONException {
        JSONObject json = new JSONObject(stringJson);
        mName = json.getString("name");
        mDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");
        mTemperature = json.getJSONObject("main").getString("temp");
        mIdCity = json.getInt("id");
        mLatitude = json.getJSONObject("coord").getDouble("lat");
        mLongitude = json.getJSONObject("coord").getDouble("lon");
        mWeatherId = json.getJSONArray("weather").getJSONObject(0).getInt("id");
        mSunrise = json.getJSONObject("sys").getInt("sunrise");
    }
}

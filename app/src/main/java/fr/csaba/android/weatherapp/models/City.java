package fr.csaba.android.weatherapp.models;

public class City {
    public String mName;
    public String mDescription;
    public String mTemperature;
    public int mWeatherIcon;

    public City(String name, String description, String temperature, int weatherIcon) {
        mName = name;
        mDescription = description;
        mTemperature = temperature;
        mWeatherIcon = weatherIcon;
    }
}

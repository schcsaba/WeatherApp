package fr.csaba.android.weatherapp.interfaces;

import org.json.JSONException;

public interface UpdateUiFunction<T> {
    void apply(T t) throws JSONException;
}

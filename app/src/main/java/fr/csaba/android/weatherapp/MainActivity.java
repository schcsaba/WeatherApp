package fr.csaba.android.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCityName;
    private LinearLayout mLinearLayoutHead;
    private Button mButtonFavorites;
    private TextView mTextViewNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewCityName = (TextView) findViewById(R.id.text_view_city_name);
        mTextViewCityName.setText(R.string.city_name);
        mLinearLayoutHead = (LinearLayout) findViewById(R.id.linear_layout_head);
        mButtonFavorites = (Button) findViewById(R.id.button_favorites);
        mTextViewNoInternet = (TextView) findViewById(R.id.text_view_no_internet);
        mTextViewNoInternet.setText(R.string.no_internet_connection);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("TAG", "Oui je suis connecté");
            mTextViewNoInternet.setVisibility(View.INVISIBLE);
        } else {
            Log.d("TAG", "Non j'ai rien du tout");
            mLinearLayoutHead.setVisibility(View.INVISIBLE);
            mButtonFavorites.setVisibility(View.INVISIBLE);
            mTextViewNoInternet.setVisibility(View.VISIBLE);
        }
    }
}
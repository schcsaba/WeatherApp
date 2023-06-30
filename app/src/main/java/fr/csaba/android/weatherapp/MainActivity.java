package fr.csaba.android.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCityName;
    private LinearLayout mLinearLayoutHead;
    private Button mButtonFavorites;
    private TextView mTextViewNoInternet;
    private EditText mEditTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewCityName = findViewById(R.id.text_view_city_name);
        mTextViewCityName.setText(R.string.city_name);
        mLinearLayoutHead = findViewById(R.id.linear_layout_head);
        mButtonFavorites = findViewById(R.id.button_favorites);
        mTextViewNoInternet = findViewById(R.id.text_view_no_internet);
        mTextViewNoInternet.setText(R.string.no_internet_connection);
        mEditTextMessage = findViewById(R.id.edit_text_message);

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
        Log.d("TAG", "MainActivity: onCreate()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "MainActivity: onDestroy()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "MainActivity: onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG", "MainActivity: onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "MainActivity: onStop()");
    }

    public void onClickButtonFavorites(View view) {
        String message = mEditTextMessage.getText().toString();
        Intent intent = new Intent(this, FavoriteActivity.class);
        intent.putExtra(Keys.MESSAGE_KEY, message);
        startActivity(intent);
    }
}
package edu.tacoma.uw.guilbb.courseswebservicesapp.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.guilbb.courseswebservicesapp.LoginFragment;
import edu.tacoma.uw.guilbb.courseswebservicesapp.ParkingAddFragment;
import edu.tacoma.uw.guilbb.courseswebservicesapp.ParkingDetailActivity;
import edu.tacoma.uw.guilbb.courseswebservicesapp.ParkingListActivity;
import edu.tacoma.uw.guilbb.courseswebservicesapp.R;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class SignInActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener, LoginFragment.RegisterFragmentListener {

    private SharedPreferences mSharedPreferences;
    private JSONObject mMemberJSON;
    private static final String TAG = "DisplayMessageActivity";
    public static final String LOGIN = "LOGIN";
    public CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        //LoginButton facebookBtn = findViewById(R.id.login_button);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Intent intent = new Intent(getApplicationContext(), ParkingListActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }

                });


        //Toast.makeText(this, "You have signed in", Toast.LENGTH_SHORT).show();
        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);
        if (!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.sign_in_fragment_id, new LoginFragment())
                    .commit();

        } else {
            Intent intent = new Intent(this, ParkingListActivity.class);
            startActivity(intent);
            finish();
        }

//
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void login(String email, String pwd) {
        //Toast.makeText(this, "You have signed in", Toast.LENGTH_SHORT).show();
        mSharedPreferences
                .edit()
                .putBoolean(getString(R.string.LOGGEDIN), true)
                .commit();
        Intent i = new Intent(this, ParkingListActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void register() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
        finish();
    }

}


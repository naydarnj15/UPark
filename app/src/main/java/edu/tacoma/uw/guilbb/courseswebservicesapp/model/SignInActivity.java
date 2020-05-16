package edu.tacoma.uw.guilbb.courseswebservicesapp.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.tacoma.uw.guilbb.courseswebservicesapp.LoginFragment;
import edu.tacoma.uw.guilbb.courseswebservicesapp.ParkingAddFragment;
import edu.tacoma.uw.guilbb.courseswebservicesapp.ParkingDetailActivity;
import edu.tacoma.uw.guilbb.courseswebservicesapp.ParkingListActivity;
import edu.tacoma.uw.guilbb.courseswebservicesapp.R;

public class SignInActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener, LoginFragment.RegisterFragmentListener {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
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


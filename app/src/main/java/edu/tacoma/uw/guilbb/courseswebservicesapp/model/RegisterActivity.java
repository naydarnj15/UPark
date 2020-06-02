package edu.tacoma.uw.guilbb.courseswebservicesapp.model;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import edu.tacoma.uw.guilbb.courseswebservicesapp.ParkingDetailFragment;
import edu.tacoma.uw.guilbb.courseswebservicesapp.ParkingListActivity;
import edu.tacoma.uw.guilbb.courseswebservicesapp.R;

public class RegisterActivity extends AppCompatActivity {

    public static final String REGISTER_MEMBER = "REGISTER_MEMBER";
    private JSONObject mMemberJSON;
    private static final String TAG = "DisplayMessageActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ParkingAddFragment courseAddFragment = new ParkingAddFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.item_detail_container, courseAddFragment).commit();
//        Intent i = new Intent(this, ParkingListActivity.class);
//        startActivity(i);
//        finish();

//        ParkingAddFragment fragment = new ParkingAddFragment();
//        //myFragment = fragment;
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.item_detail_container, fragment)
//                .commit();

        //final Member thisMember = (Member) getIntent().getSerializableExtra(ParkingDetailFragment.ARG_ITEM_ID);


        //final EditText firstNameEditText = this.findViewById(R.id.add_first_name);
        //final EditText lastNameEditText = this.findViewById(R.id.add_last_name);
        final EditText emailEditText = this.findViewById(R.id.add_email);
        //final EditText usernameEditText = this.findViewById(R.id.add_username);
        final EditText passwordEditText = this.findViewById(R.id.add_password);


        Button registerButton = this.findViewById(R.id.btn_register);
        View.OnClickListener registerButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "adding member...");
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (TextUtils.isEmpty(email) || !email.contains("@")) {
                    Toast.makeText(v.getContext(), "Enter valid email address", Toast.LENGTH_SHORT).show();
                    emailEditText.requestFocus();
                }else if (TextUtils.isEmpty(password) || password.length() < 6) {
                    Toast.makeText(v.getContext(), "Enter a valid password (at least 6 characters)", Toast.LENGTH_SHORT).show();
                    passwordEditText.requestFocus();
                } else {
                    registerMember(email, password);

                }
            }
        };
        registerButton.setOnClickListener (registerButtonOnClickListener);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private class RegisterMemberAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);
                    OutputStreamWriter wr =
                            new OutputStreamWriter(urlConnection.getOutputStream());

                    // For Debugging
                    Log.i(REGISTER_MEMBER, mMemberJSON.toString());
                    wr.write(mMemberJSON.toString());
                    wr.flush();
                    wr.close();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add the new course, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.startsWith("Unable to add the new course")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(), "Course Added successfully"
                            , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Course couldn't be added: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();
                    Log.e(REGISTER_MEMBER, jsonObject.getString("error"));
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on Adding course"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.e(REGISTER_MEMBER, e.getMessage());
            }
        }
    }


    public void registerMember(String email, String password){
        StringBuilder url = new StringBuilder(getString(R.string.register_course));
        //Log.i(TAG, "entered registerMember method");
        //Construct a JSONObject to build a formatted message to send
        mMemberJSON = new JSONObject();

        try{

            Log.i(TAG, "entered registerMember method");
            mMemberJSON.put(Member.EMAIL, email);
            mMemberJSON.put(Member.PASSWORD, password);
            new RegisterActivity.RegisterMemberAsyncTask().execute(url.toString());


        }catch (JSONException e){
            Toast.makeText(this, "Error with JSON creation on adding a member: "
                            + e.getMessage()
                    ,Toast.LENGTH_SHORT).show();
        }
    }
}

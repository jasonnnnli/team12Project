package edu.tacoma.uw.jasonli7.team12project.authenticate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.jasonli7.team12project.R;
import edu.tacoma.uw.jasonli7.team12project.main.DeviceListActivity;
/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 2nd Aug 2020.
 *
 * An activity to process and communicate login data.
 */
public class SignInActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener {
private SharedPreferences mSharedPreferences;

        private JSONObject mLogin;
        private String mUserName;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS),
        Context.MODE_PRIVATE);
        getSupportFragmentManager().beginTransaction()
        .add(R.id.sign_in_fragment_id, new LoginFragment())
        .commit();
    }

        /**
         * Calls server /login.
         *
         * @param email passed from fragment by user.
         * @param pwd   passed from fragment by user.
         */
        @Override
public void login(String email, String pwd) {
        StringBuilder url = new StringBuilder(getString(R.string.add_login));
        mSharedPreferences
                .edit()
                .putBoolean(getString(R.string.LOGGEDIN), true)
                .commit();
        mLogin = new JSONObject();

        try {

                mLogin.put("email", email);
                mLogin.put("password", pwd);
                mUserName =email;
                new LoginAsyncTask().execute(url.toString());

        } catch (JSONException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
}
        private void goToMain() {
                Intent intent = new Intent(this, DeviceListActivity.class);
                startActivity(intent);
                finish();
        }

        /**
         * Parses and sends json objects.
         */
        private class LoginAsyncTask extends AsyncTask<String, Void, String> {
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

                                        wr.write(mLogin.toString());
                                        wr.flush();
                                        wr.close();

                                        InputStream content = urlConnection.getInputStream();

                                        BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                                        String s = "";
                                        while ((s = buffer.readLine()) != null) {
                                                response += s;
                                        }

                                } catch (Exception e) {
                                        response = "Unable to login, Reason: "
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
                        if (s.startsWith("Unable to login")) {
                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                                return;
                        }
                        try {
                                JSONObject jsonObject = new JSONObject(s);
                                if (jsonObject.getBoolean("success")) {
                                      String st =  jsonObject.getString("members");
                                        mSharedPreferences
                                                .edit()
                                                .putString("USERID",  st)
                                                .apply();
                                        Toast.makeText(getApplicationContext(), "User login successful"// + st
                                                , Toast.LENGTH_SHORT).show();
                                       // SharedPreferences share = getSharedPreferences(getString(R.string.USERID_PREFS),
                                               // Context.MODE_PRIVATE);


                                        goToMain();
                                }
                                else {
                                        Toast.makeText(getApplicationContext(), "Could not login: "
                                                        + jsonObject.getString("error")
                                                , Toast.LENGTH_LONG).show();

                                }
                        } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "JSON Parsing error on login "
                                                + e.getMessage()
                                        , Toast.LENGTH_LONG).show();

                        }
                }
        }
}
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
import edu.tacoma.uw.jasonli7.team12project.model.InfoHolder;
/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 2nd Aug 2020.
 *
 * An activity to process and communicate register data.
 */
public class RegisterActivity extends AppCompatActivity implements RegisterFragment.RegisterFragmentListener {
    private SharedPreferences mSharedPreferences;
    private boolean mTest = true;
    private JSONObject mAddUser;
    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS),
                Context.MODE_PRIVATE);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.reg_fragment_id, new RegisterFragment())
                .commit();
    }

    /**
     * Calls server /register.
     *
     * @param first
     * @param last
     * @param email
     * @param userName
     * @param pwd
     */
    @Override
    public void register(String first, String last, String email, String userName, String pwd) {
        StringBuilder url = new StringBuilder(getString(R.string.add_register));

        mAddUser = new JSONObject();

        try {
            mAddUser.put("first", first);
            mAddUser.put("last", last);
            mAddUser.put("email", email);
            mAddUser.put("username", userName);
            mAddUser.put("password", pwd);
            mUserName =email;
            new  AddUserAsyncTask().execute(url.toString());

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Helper method calls DeviceListActivity.
     */
    private void goToMain() {
        Intent intent = new Intent(this, DeviceListActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Calls server /register, parses json objects.
     */
    private class AddUserAsyncTask extends AsyncTask<String, Void, String> {
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

                    wr.write(mAddUser.toString());
                    wr.flush();
                    wr.close();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add the new user, Reason: "
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
            if (s.startsWith("Unable to add the new user")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(), "User Added successfully"
                            , Toast.LENGTH_SHORT).show();
                   goToMain();
                }
                else {
                    Toast.makeText(getApplicationContext(), "User couldn't be added: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on Adding user"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();

            }
        }
    }
}
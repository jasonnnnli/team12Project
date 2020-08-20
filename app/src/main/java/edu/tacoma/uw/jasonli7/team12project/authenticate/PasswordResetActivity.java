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
 * @version 17th Aug 2020.
 *
 * Activity to send user reset password data.
 */
public class PasswordResetActivity extends AppCompatActivity implements  PasswordResetFragment.ResetListener {
    private SharedPreferences mSharedPreferences;
    private JSONObject mAddUser;
    private String mUserName;

    /**
     * Launches PasswordResetFragment.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS),
                Context.MODE_PRIVATE);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.reset_fragment_id, new PasswordResetFragment())
                .commit();
    }

    /**
     * Implemented from listener.
     *
     * @param email
     * @param name
     * @param pwd
     */
    @Override
    public void reset(String email, String name, String pwd) {
        StringBuilder url = new StringBuilder(getString(R.string.add_reset));

        mAddUser = new JSONObject();

        try {
            mAddUser.put("email", email);
            mAddUser.put("firstname", name);
            mAddUser.put("newPassword", pwd);

            new ResetAsyncTask().execute(url.toString());

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
     * Calls server /changepassword, parses json objects.
     */
    private class ResetAsyncTask extends AsyncTask<String, Void, String> {
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
                    response = "Unable to change password, Reason: "
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
            if (s.startsWith("Unable to change password")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(), "Password changed successfully"
                            , Toast.LENGTH_SHORT).show();
                    goToMain();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Password couldn't be changed: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on changing password"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();

            }
        }
    }
}

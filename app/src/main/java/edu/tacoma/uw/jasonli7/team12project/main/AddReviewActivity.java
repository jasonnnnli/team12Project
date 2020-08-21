package edu.tacoma.uw.jasonli7.team12project.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.jasonli7.team12project.R;
import edu.tacoma.uw.jasonli7.team12project.model.Review;

/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 17th Aug 2020.
 *
 * An activity for adding new review data to the server.
 */
public class AddReviewActivity extends AppCompatActivity implements AddReviewFragment.AddReviewListener {

    private JSONObject mReview;
    SharedPreferences mPrefs;

    /**
     * Sets up fragment passes device name.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        mPrefs = getSharedPreferences(getString(R.string.LOGIN_PREFS),
                Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.top);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        Bundle arguments = new Bundle();
        arguments.putString(AddReviewFragment.ARG_REGISTER,
                getIntent().getStringExtra(AddReviewFragment.ARG_REGISTER));
        arguments.putString(AddReviewFragment.ARG_USER,
               String.valueOf(mPrefs.getString("USERID", "string")));
        AddReviewFragment fragment = new AddReviewFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.add_Review_fragment_id, fragment)
                .commit();
    }

    /**
     * Sets up json object -implemented by AddReviewListener.
     *
     * @param review
     */
    @Override
    public void addReview(Review review) {
        StringBuilder url = new StringBuilder(getString(R.string.add_review));

        mReview = new JSONObject();

        try {

            mReview.put("Username", review.getmUserName());
            mReview.put("Devicename", review.getmDeviceName());
            mReview.put("Reviewcontent", review.getmReview());
            mReview.put("Rating", review.getRate());


            new ReviewAsyncTask().execute(url.toString());

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * helper method to return to DeviceListActivity.
     */
    private void goToMain() {
        Intent intent = new Intent(this, DeviceListActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * Parses and sends json objects.
     */
    private class ReviewAsyncTask extends AsyncTask<String, Void, String> {
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

                    wr.write(mReview.toString());
                    wr.flush();
                    wr.close();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to post review, Reason: "
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
            if (s.startsWith("Unable to post review")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(), "User post uploaded"
                            , Toast.LENGTH_SHORT).show();
                    goToMain();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Could not post review: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on write review "
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();

            }
        }
    }
}
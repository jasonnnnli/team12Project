package edu.tacoma.uw.jasonli7.team12project.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.HashMap;
import java.util.Random;

import edu.tacoma.uw.jasonli7.team12project.R;
import edu.tacoma.uw.jasonli7.team12project.model.DeviceContent;
import edu.tacoma.uw.jasonli7.team12project.model.Features;
/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 18th Aug 2020.
 *
 * Activity to add features.
 */
public class AddFeatureActivity extends AppCompatActivity implements AddFeatureFragment.AddFeatureListener {

    public static final String PASS_DEVICE = "pass_device";
    private String mDevice;
    private JSONObject mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feature);
        mDevice =  getIntent().getStringExtra(PASS_DEVICE);
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(AddFeatureFragment.ARG_FEATURE_ID,
                    mDevice);
            AddFeatureFragment fragment = new AddFeatureFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.add_feature_fragment_id, fragment)
                    .commit();
        }
    }

    /**
     * Implemented from add feature listener.
     *
     * @param feature
     */
    @Override
    public void addFeature(Features feature) {
        StringBuilder url = new StringBuilder("https://team12-services-backend.herokuapp.com/addfeature");

        mAdd = new JSONObject();

        try {

            mAdd.put("Devicename",feature.getDevice());
            mAdd.put("featurecontent", feature.getFeature());


            new addFeatureAsyncTask().execute(url.toString());

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
     * Creates and sends json object.
     */
    private class addFeatureAsyncTask extends AsyncTask<String, Void, String> {
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

                    wr.write(mAdd.toString());
                    wr.flush();
                    wr.close();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add feature, Reason: "
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
            if (s.startsWith("Unable to add")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(), "Feature added successfully"
                            , Toast.LENGTH_SHORT).show();
                    Features.FEATURE_MAP = new HashMap<>();
                    goToMain();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Could not add feature: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on add device "
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();

            }
        }
    }


}
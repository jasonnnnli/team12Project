package edu.tacoma.uw.jasonli7.team12project.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import edu.tacoma.uw.jasonli7.team12project.R;
import edu.tacoma.uw.jasonli7.team12project.model.Device;
import edu.tacoma.uw.jasonli7.team12project.model.DeviceContent;

/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 2nd Aug 2020.
 *
 * An activity for communicating new device data to the server.
 */
public class AddDeviceActivity extends AppCompatActivity  implements AddDeviceFragment.AddDeviceListener {
    private JSONObject mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        AddDeviceFragment fragment = new AddDeviceFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.add_Device_fragment_id, fragment)
                .commit();
    }

    /**
     * interface method. adds device to the database.
     *
     * @param device
     */
    @Override
    public void addDevice(Device device) {
        StringBuilder url = new StringBuilder(getString(R.string.add_devices));

        mAdd = new JSONObject();

        try {

            mAdd.put("Devicename",device.getDeviceName());
            mAdd.put("Decicedetail", new Random(111).nextDouble());
            mAdd.put("Deciceprice", DeviceContent.mockPrice());

            new addDeviceAsyncTask().execute(url.toString());

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
    private class addDeviceAsyncTask extends AsyncTask<String, Void, String> {
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
                    response = "Unable to add device, Reason: "
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
                    Toast.makeText(getApplicationContext(), "Device added successfully"
                            , Toast.LENGTH_SHORT).show();
                    goToMain();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Could not add device: "
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
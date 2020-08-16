package edu.tacoma.uw.jasonli7.team12project.main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.tacoma.uw.jasonli7.team12project.R;

import edu.tacoma.uw.jasonli7.team12project.model.Device;
import edu.tacoma.uw.jasonli7.team12project.model.DeviceContent;
import edu.tacoma.uw.jasonli7.team12project.model.InfoHolder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 3rd Aug 2020.
 *
 * An activity that creates a scrolling list of clickable Devices.
 */
public class DeviceListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    RecyclerView mRecyclerView;
    private boolean mTwoPane;
    private List<Device> mDeviceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             launchDeviceAddFragment();
            }
        });

        if (findViewById(R.id.device_detail_container) != null) {
            mTwoPane = true;
        }

         mRecyclerView = findViewById(R.id.device_list);
        assert mRecyclerView != null;
        setupRecyclerView((RecyclerView) mRecyclerView);
    }

    /**
     * Gets ready to phone home.
     */
    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (mDeviceList == null) {
                new DeviceTask().execute(getString(R.string.get_devices));
            }
        }

    }

    /**
     * Helper method calls AddDeviceActivity.
     */
    private void launchDeviceAddFragment() {
        Intent intent = new Intent(this, AddDeviceActivity.class);
        startActivity(intent);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        if (mDeviceList != null) {
            DeviceContent.priceSort(false); //__________________________--------------->test sorts!
            mDeviceList = DeviceContent.ITEMS;
            mRecyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(
                    this, mDeviceList, mTwoPane));
        }
    }

    /**
     * Recycler adapter class.
     */
    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final DeviceListActivity mParentActivity;
        private final List<Device> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Device item = (Device) view.getTag();
                //InfoHolder.InfoPass.setReviewList(item.getReviews());
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(DeviceDetailFragment.ARG_ITEM_ID, item.getDeviceName());
                    DeviceDetailFragment fragment = new DeviceDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.device_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DeviceDetailActivity.class);
                    intent.putExtra(DeviceDetailFragment.ARG_ITEM_ID, item.getDeviceName());

                    context.startActivity(intent);
                }
            }
        };

        /**
         * Recycler view adapter.
         *
         * @param parent
         * @param items
         * @param twoPane
         */
        SimpleItemRecyclerViewAdapter(DeviceListActivity parent,
                                      List<Device> items,
                                      boolean twoPane) {

            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        /**
         * unchanged.
         *
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.device_list_content, parent, false);
            return new ViewHolder(view);
        }

        /**
         * Unchanged.
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getDeviceName());
            DecimalFormat format = new DecimalFormat("##.00");
            holder.mContentView.setText(String.valueOf(format.format(mValues.get(position).getAvgRate())+
                    "     $ " + String.valueOf(format.format(mValues.get(position).getPrice()))));

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        /**
         * Unchanged.
         *
         * @return
         */
        @Override
        public int getItemCount() {
            return mValues.size();
        }

        /**
         * unchanged.
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }

    /**
     * Connects to and loads json objects.
     */
    private class DeviceTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of devices, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;

        }

        /**
         * Calls device fore parsing.
         *
         * @param s
         */
        protected void onPostExecute(String s) {
            if (s.startsWith("Unable to")) {
                mDeviceList = DeviceContent.ITEMS;
                Toast.makeText(getApplicationContext(), "Unable to download" + s, Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getBoolean("success")) {
                    mDeviceList = Device.parseDeviceJson(
                            jsonObject.getString("Devices"));



                    if (!mDeviceList.isEmpty()) {
                        setupRecyclerView((RecyclerView) mRecyclerView);
                    }
                }

            } catch (JSONException e) {
                mDeviceList = DeviceContent.ITEMS;
                Toast.makeText(getApplicationContext(), "JSON Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
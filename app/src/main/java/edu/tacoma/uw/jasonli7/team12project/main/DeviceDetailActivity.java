package edu.tacoma.uw.jasonli7.team12project.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import edu.tacoma.uw.jasonli7.team12project.R;

/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 3rd Aug 2020.
 *
 * An activity for coordinating device data.
 */
public class DeviceDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String ADD_DEVICE = "ADD_DEVICE";
    private Button mBut;

    /**
     * Does some of the heavy lifting for DeviceFragment.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        mBut =findViewById(R.id.reviews_btn);
        mBut.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(DeviceDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(DeviceDetailFragment.ARG_ITEM_ID));
            DeviceDetailFragment fragment = new DeviceDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.device_detail_container, fragment)
                    .commit();
        }  else if (getIntent().getBooleanExtra(DeviceDetailActivity.ADD_DEVICE, false)) {
            AddDeviceFragment fragment = new AddDeviceFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.device_detail_container, fragment).commit();
        }
    }

    /**
     * return navigation to home.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            navigateUpTo(new Intent(this, DeviceListActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Go to reviews for selected device.
     */
    @Override
    public void onClick(View view) {
       // Intent intent = new Intent(this, ReviewListActivity.class);
       // startActivity(intent);
        //Device item = (Device) view.getTag();
        Context context = view.getContext();
        Intent intent = new Intent(context, ReviewListActivity.class);
        intent.putExtra(ReviewListActivity.ARG_Device_ID, getIntent().getStringExtra(DeviceDetailFragment.ARG_ITEM_ID));

        context.startActivity(intent);
    }
}
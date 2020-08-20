package edu.tacoma.uw.jasonli7.team12project.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private String mDevice;

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
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "Hey, what do you think about the " + mDevice+"?";
                String shareSub = "Get advice from a friend";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Ask a friend with:"));
            }
        });


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        mDevice =  getIntent().getStringExtra(DeviceDetailFragment.ARG_ITEM_ID);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(DeviceDetailFragment.ARG_ITEM_ID,
                    mDevice);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feature_menu, menu);
        return true;
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
        if (id == R.id.feature_list) {
            Intent intent = new Intent(this, FeaturesActivity.class);
            intent.putExtra(FeaturesActivity.FEATURE_DEVICE, getIntent().getStringExtra(DeviceDetailFragment.ARG_ITEM_ID));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Go to reviews for selected device.
     */
    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        Intent intent = null;
        if (view.getId() == R.id.reviews_btn) {
            
            intent = new Intent(context, ReviewListActivity.class);
            intent.putExtra(ReviewListActivity.ARG_Device_ID, getIntent().getStringExtra(DeviceDetailFragment.ARG_ITEM_ID));

        }
        context.startActivity(intent);
    }
}
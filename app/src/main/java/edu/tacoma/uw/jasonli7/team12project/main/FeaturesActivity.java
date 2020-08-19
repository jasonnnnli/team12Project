package edu.tacoma.uw.jasonli7.team12project.main;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import java.util.ArrayList;

import edu.tacoma.uw.jasonli7.team12project.R;
import edu.tacoma.uw.jasonli7.team12project.model.Features;

public class FeaturesActivity extends AppCompatActivity {
    public static final String FEATURE_DEVICE = "feature_device";
    private String mDevice;
    ArrayList<String> mFeaturesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);
        mDevice = getIntent().getStringExtra(FEATURE_DEVICE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(mDevice + " features:");
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(FeaturesFragment.ARG_FEATURE_ID,
                    mDevice);
            FeaturesFragment fragment = new FeaturesFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.feature_detail_container, fragment)
                    .commit();
        }  else if (getIntent().getBooleanExtra(FeaturesActivity.FEATURE_DEVICE, false)) {
            FeaturesFragment fragment = new FeaturesFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.feature_detail_container, fragment).commit();
        }
    }

}
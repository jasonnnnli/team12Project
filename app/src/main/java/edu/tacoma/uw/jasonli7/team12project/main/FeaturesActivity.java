package edu.tacoma.uw.jasonli7.team12project.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import edu.tacoma.uw.jasonli7.team12project.R;
import edu.tacoma.uw.jasonli7.team12project.authenticate.SignInActivity;
import edu.tacoma.uw.jasonli7.team12project.model.Features;

public class FeaturesActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String FEATURE_DEVICE = "feature_device";
    private String mDevice;
    ArrayList<String> mFeaturesList;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);
        mDevice = getIntent().getStringExtra(FEATURE_DEVICE);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle(mDevice + " features:");
        setSupportActionBar(toolbar);

        mLoginButton =findViewById(R.id.button3);
        mLoginButton.setOnClickListener(this);
        if(mDevice != null) {
            //Features.loadMap();
            StringBuilder sb = new StringBuilder();
            if (Features.exists(mDevice)) {

                mFeaturesList = Features.getFeatures(mDevice);
                TextView rootView = findViewById(R.id.textView3);

                for (int i = 0; i < mFeaturesList.size(); i++) {
                    sb.append(mFeaturesList.get(i) + "\n");
                }
                rootView.setText(sb.toString());
            } else {
                mFeaturesList = new ArrayList<>();
                mFeaturesList.add("Device has no listed features yet.");
            }

        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button3) {

            Intent intent = new Intent(this, AddFeatureActivity.class);
            intent.putExtra(AddFeatureActivity.PASS_DEVICE, getIntent().getStringExtra(FeaturesActivity.FEATURE_DEVICE));
            startActivity(intent);

        }
    }
}
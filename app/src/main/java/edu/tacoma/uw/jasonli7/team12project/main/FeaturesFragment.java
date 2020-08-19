package edu.tacoma.uw.jasonli7.team12project.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.tacoma.uw.jasonli7.team12project.R;
import edu.tacoma.uw.jasonli7.team12project.model.Device;
import edu.tacoma.uw.jasonli7.team12project.model.DeviceContent;
import edu.tacoma.uw.jasonli7.team12project.model.Features;

public class FeaturesFragment extends Fragment {

    public static final String ARG_FEATURE_ID = "features_id";
    private String mDevice;
    private ArrayList<String> mFeaturesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_FEATURE_ID)) {

            mDevice = getArguments().getString(ARG_FEATURE_ID);


        }
    }
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_features, container, false);
        // Inflate the layout for this fragment
        if(mDevice != null) {
            if (Features.exists(mDevice)) {
                mFeaturesList = Features.getFeatures(mDevice);

                    DecimalFormat format = new DecimalFormat("##.00");

                    ((TextView) rootView.findViewById(R.id.feature_detail_container)).setText(mFeaturesList.get(0));

            } else {
                mFeaturesList = new ArrayList<>();
                mFeaturesList.add("Device has no listed features yet.");
            }

        }
        return inflater.inflate(R.layout.fragment_features, container, false);
    }


}
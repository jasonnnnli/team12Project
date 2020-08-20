package edu.tacoma.uw.jasonli7.team12project.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.tacoma.uw.jasonli7.team12project.R;
import edu.tacoma.uw.jasonli7.team12project.model.Device;
import edu.tacoma.uw.jasonli7.team12project.model.DeviceContent;
import edu.tacoma.uw.jasonli7.team12project.model.Features;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFeatureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFeatureFragment extends Fragment {
    public static final String ARG_FEATURE_ID = "device_name";
    private String mDevice;
    Activity mActivity;
    private AddFeatureFragment.AddFeatureListener mAddFeatureListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFeatureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFeatureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFeatureFragment newInstance(String param1, String param2) {
        AddFeatureFragment fragment = new AddFeatureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDevice = getArguments().getString(ARG_FEATURE_ID);
        mAddFeatureListener = (AddFeatureFragment.AddFeatureListener) getActivity();
        mActivity = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_feature, container
                , false);
        getActivity().setTitle("Add a New Device");
        final EditText addFeature = v.findViewById(R.id.add_feature);
        Button addButton = v.findViewById(R.id.btn_add_feature);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feature = addFeature.getText().toString();

                Features features = new Features(mDevice, feature);

                if (mAddFeatureListener != null) {
                    mAddFeatureListener.addFeature(features);
                }
            }
        });
        return v;
    }
    public interface AddFeatureListener {
        public  void  addFeature(Features device);
    }
}
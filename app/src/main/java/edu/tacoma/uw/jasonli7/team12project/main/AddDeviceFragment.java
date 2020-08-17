package edu.tacoma.uw.jasonli7.team12project.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import edu.tacoma.uw.jasonli7.team12project.R;
import edu.tacoma.uw.jasonli7.team12project.model.Device;
import edu.tacoma.uw.jasonli7.team12project.model.DeviceContent;

/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 3rd Aug 2020.
 *
 * A fragment add new device data.
 */
public class AddDeviceFragment extends Fragment {
    private AddDeviceListener mAddDeviceListener;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AddDeviceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseAddFragment.
     */
    public static AddDeviceFragment newInstance(String param1, String param2) {
        AddDeviceFragment fragment = new AddDeviceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddDeviceListener = (AddDeviceListener) getActivity();
    }

    /**
     * Gets user input and sends it via the listener to the parser.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_device, container
                , false);
        getActivity().setTitle("Add a New Device");
        final EditText addDev = v.findViewById(R.id.add_device);
        Button addButton = v.findViewById(R.id.btn_add_device);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String devices = addDev.getText().toString();

                Device device = new Device(devices, DeviceContent.mReviews, DeviceContent.mockPrice());

                if (mAddDeviceListener != null) {
                    mAddDeviceListener.addDevice(device);
                }
            }
        });
        return v;
    }

    /**
     * Listener interface.
     */
    public interface AddDeviceListener {
        public  void  addDevice(Device device);
    }
}
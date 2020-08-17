package edu.tacoma.uw.jasonli7.team12project.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.tacoma.uw.jasonli7.team12project.R;
import edu.tacoma.uw.jasonli7.team12project.model.Device;
import edu.tacoma.uw.jasonli7.team12project.model.InfoHolder;
import edu.tacoma.uw.jasonli7.team12project.model.Review;

/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 3rd Aug 2020.
 *
 * A fragment add new review data.
 */
public class AddReviewFragment extends Fragment {

    public static String ARG_REGISTER = "register";
    private String mDeviceName;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    Activity mActivity;
    public AddReviewFragment() {
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
    public static AddReviewFragment newInstance(String param1, String param2) {
        AddReviewFragment fragment = new AddReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    //commented calls are to connect button to AddReviewActivity.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddReviewListener = (AddReviewListener) getActivity();
        mActivity = this.getActivity();
        if (getArguments().containsKey(ARG_REGISTER)) {
            mDeviceName =  getArguments().getString(ARG_REGISTER);
        } else {
            mDeviceName = "Test Phone";
        }

    }
    //commented calls are to connect button to AddReviewActivity.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_review, container
                , false);
        getActivity().setTitle("Signed in as: " +InfoHolder.InfoPass.getmEmail());

        final EditText userId = v.findViewById(R.id.add_user_id);
        final EditText addReview = v.findViewById(R.id.add_review);
        final EditText rating = v.findViewById(R.id.editTextNumber);
        Button addButton = v.findViewById(R.id.btn_add_review);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uID = userId.getText().toString();
                String rev = addReview.getText().toString();
                String rat = rating.getText().toString();
                double x = Double.parseDouble(rat);
                if (mAddReviewListener != null && (x <= 5 && x >= 0 )) {
                    mAddReviewListener.addReview(new Review(uID, mDeviceName, rev, x));
                } else {
                    Toast.makeText(mActivity, "Please enter a number between 0-5", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
    private AddReviewFragment.AddReviewListener mAddReviewListener;
    public interface AddReviewListener {
        public  void  addReview(Review review);
    }
}
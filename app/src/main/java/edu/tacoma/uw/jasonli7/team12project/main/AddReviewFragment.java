package edu.tacoma.uw.jasonli7.team12project.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import edu.tacoma.uw.jasonli7.team12project.R;
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
    public static String ARG_USER = "user";
    private String mDeviceName;
    private String mUserId;
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

    /**
     * Collects device name.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddReviewListener = (AddReviewListener) getActivity();

        mActivity = this.getActivity();
        if (getArguments().containsKey(ARG_REGISTER)) {
            mDeviceName =  getArguments().getString(ARG_REGISTER);
        }
        if (getArguments().containsKey(ARG_USER)) {
            mUserId =  getArguments().getString(ARG_USER);
        }

    }

    /**
     * Gets fields to add a review.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_review, container
                , false);
        getActivity().setTitle("Signed in as: " +mUserId);

        final TextView userId = v.findViewById(R.id.add_user_id);
        final EditText addReview = v.findViewById(R.id.add_review);
        final EditText rating = v.findViewById(R.id.editTextNumber);
        Button addButton = v.findViewById(R.id.btn_add_review);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId.setText(mUserId);
                String rev = addReview.getText().toString();
                String rat = rating.getText().toString();
                double x = Double.parseDouble(rat);
                if (mAddReviewListener != null && (x <= 5 && x >= 0 )) {
                    mAddReviewListener.addReview(new Review(mUserId, mDeviceName, rev, x));
                } else {
                    Toast.makeText(mActivity, "Please enter a number between 0-5", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    /**
     * Interface to add listener.
     */
    private AddReviewFragment.AddReviewListener mAddReviewListener;
    public interface AddReviewListener {
        public  void  addReview(Review review);
    }
}
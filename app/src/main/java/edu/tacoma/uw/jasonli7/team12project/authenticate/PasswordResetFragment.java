package edu.tacoma.uw.jasonli7.team12project.authenticate;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.tacoma.uw.jasonli7.team12project.R;
/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 17th Aug 2020.
 *
 * A fragment to handle reset password data.
 */
public class PasswordResetFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ResetListener mAddListener;
    private Activity mActivity;
    private String mParam1;
    private String mParam2;

    public PasswordResetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PasswordResetFragment.
     */
    public static PasswordResetFragment newInstance(String param1, String param2) {
        PasswordResetFragment fragment = new PasswordResetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddListener = (ResetListener) getActivity();
        mActivity = this.getActivity();
    }

    /**
     * Contains listener. gathers password reset information from the user.
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
        View v = inflater.inflate(R.layout.fragment_password_reset, container
                , false);
        getActivity().setTitle("Reset Password");

        final EditText addEmail = v.findViewById(R.id.password_);
        final EditText addUser = v.findViewById(R.id.first_);
        final EditText addPw = v.findViewById(R.id.pass);
        final EditText confirm = v.findViewById(R.id.confirm);
        Button addButton = v.findViewById(R.id.btn_reset);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String email = addEmail.getText().toString();
             String user = addUser.getText().toString();
             String pw = addPw.getText().toString();
             String con = confirm.getText().toString();

             if (mAddListener != null && pw.equals(con)) {
                 mAddListener.reset(email, user, email);
             } else {

                 Toast.makeText(mActivity, "Passwords do not match", Toast.LENGTH_LONG).show();
             }
            }
    });
        return v;
    }

    /**
     * Listener interface.
     */
    public interface ResetListener {
        public void reset(String email, String name, String pwd);
    }
}
package edu.tacoma.uw.jasonli7.team12project.authenticate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import edu.tacoma.uw.jasonli7.team12project.R;

/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 2nd Aug 2020.
 *
 * A fragment to handle registration data.
 */
public class RegisterFragment extends Fragment {
    private RegisterFragmentListener mAddListener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddListener = (RegisterFragmentListener) getActivity();
    }

    /**
     * Contains listener. gathers regestration information from the user.
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
        View v = inflater.inflate(R.layout.fragment_register, container
                , false);
        getActivity().setTitle("Register new account");
        final EditText addFirst = v.findViewById(R.id.add_first);
        final EditText addLast = v.findViewById(R.id.add_last);
        final EditText addEmail = v.findViewById(R.id.add_email);
        final EditText addUser = v.findViewById(R.id.add_user_name);
        final EditText addPw = v.findViewById(R.id.add_password);
        Button addButton = v.findViewById(R.id.btn_add_user);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = addFirst.getText().toString();
                String last = addLast.getText().toString();
                String email = addEmail.getText().toString();
                String user = addUser.getText().toString();
                String pw = addPw.getText().toString();

                if (mAddListener != null) {
                    mAddListener.register(first, last, email, user, pw);
                }
            }
        });
        return v;
    }

    /**
     * Listener interface.
     */
    public interface RegisterFragmentListener {
        public void register(String first, String last, String email, String userName, String pwd);
    }
}
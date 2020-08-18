package edu.tacoma.uw.jasonli7.team12project.authenticate;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import edu.tacoma.uw.jasonli7.team12project.R;
import edu.tacoma.uw.jasonli7.team12project.model.InfoHolder;

/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 2nd Aug 2020.
 *
 * A fragment to handle login data.
 */
public class LoginFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LoginFragmentListener mLoginFragmentListener;

    /**
     * Listener interface.
     */
    public interface  LoginFragmentListener {
        public void login(String email, String pwd);
    }

    private String mParam1;
    private String mParam2;

    public LoginFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLoginFragmentListener = (LoginFragmentListener) getActivity();
        }
    }

    /**
     * Contains listener, gathers text from user.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("Sign In");
        mLoginFragmentListener = (LoginFragmentListener) getActivity();
        final  EditText emailText = view.findViewById(R.id.email_address_id);
        final EditText pwdText = view.findViewById(R.id.password_id);
        Button loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();
                //InfoHolder.InfoPass.setEmail(email);
                String pwd = pwdText.getText().toString();
                if (TextUtils.isEmpty((email)) || !email.contains("@")) {
                    Toast.makeText(v.getContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    emailText.requestFocus();
                } else if (TextUtils.isEmpty(pwd) || pwd.length() < 6) {
                    Toast.makeText(v.getContext(), "Enter valid password (at least 6 characters)", Toast.LENGTH_SHORT).show();
                    pwdText.requestFocus();
                }
                mLoginFragmentListener.login(email, pwd);
            }
        });
        return view;
    }
}
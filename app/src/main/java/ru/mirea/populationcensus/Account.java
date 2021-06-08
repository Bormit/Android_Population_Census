package ru.mirea.populationcensus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ru.mirea.populationcensus.Model.Users;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account.
     */
    // TODO: Rename and change types and number of parameters
    public static Account newInstance(String param1, String param2) {
        Account fragment = new Account();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        TextView UserName = (TextView) view.findViewById(R.id.User_name);
        Button exit_button = (Button) view.findViewById(R.id.exit_btn);
        Button admin_button = (Button) view.findViewById(R.id.admin_btn);

        if(Users.root != "admin"){
            admin_button.setVisibility(View.GONE);
        }

        UserName.setText("Привет, " + Users.login_name);

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExitAccount();
                Profile profile2Fragment = new Profile();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,profile2Fragment);
                transaction.commit();
            }
        });

        admin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    admin_panel admin_panelFragment = new admin_panel();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container,admin_panelFragment);
                    transaction.commit();
            }
        });

        return view;
    }

    private void ExitAccount(){
        Users.isUserAuthorized=false;
        Users.login_name="";
        Toast.makeText(getActivity(), "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
    }
}
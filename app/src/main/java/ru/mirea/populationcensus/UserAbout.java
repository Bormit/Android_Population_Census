package ru.mirea.populationcensus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserAbout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAbout extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView text_login;

    public UserAbout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserAbout.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAbout newInstance(String param1, String param2) {
        UserAbout fragment = new UserAbout();
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
        View view = inflater.inflate(R.layout.fragment_user_about, container, false);

        text_login = view.findViewById(R.id.users_login_text);
        Button dell_users = (Button) view.findViewById(R.id.admin_dell_btn);

        dell_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String login_dell = text_login.getText().toString();
                    DatabaseReference dUsers = FirebaseDatabase.getInstance("https://population-census-d9478-default-rtdb.firebaseio.com/").getReference("Users");
                    dUsers.child(login_dell).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Fragment fr = new admin_panel();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragment_container,fr);
                                transaction.commit();
                            }
                        }
                    });
            }
        });

        String login = getArguments().getString("login");
        text_login.setText(login);

        return view;
    }
}
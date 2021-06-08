package ru.mirea.populationcensus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ru.mirea.populationcensus.Model.Users;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText loginInput,passInput;
    private String parentDbName = "Users";

    public Profile() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        View view = inflater.inflate(R.layout.fragment_profile2, container, false);
        Button reg_button = (Button) view.findViewById(R.id.reg_btn);
        Button login_button = (Button) view.findViewById(R.id.login_btn);
        loginInput = (EditText) view.findViewById(R.id.login_login_input);
        passInput = (EditText) view.findViewById(R.id.login_password_input);
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAccount();
            }
        });
        return view;
    }

    private void CreateAccount(){
        String login = loginInput.getText().toString();
        String pass = passInput.getText().toString();

        if(TextUtils.isEmpty(login)) {
            Toast.makeText(getActivity(), "Введите логин", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pass)){
            Toast.makeText(getActivity(), "Введите пароль", Toast.LENGTH_SHORT).show();
        }else if(login.length()<5 || login.length()>20){
            Toast.makeText(getActivity(), "Длинна логина от 5 до 20 символов", Toast.LENGTH_SHORT).show();
        }else if(pass.length()<10 || pass.length()>30){
            Toast.makeText(getActivity(), "Длинна пароля от 10 до 30 символов", Toast.LENGTH_SHORT).show();
        }
        else{
            ValidateLogin(login,pass);
        }
    }

    private void ValidateLogin(final String login,final String pass){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance("https://population-census-d9478-default-rtdb.firebaseio.com/").getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(login).exists()))
                {
                    HashMap<String,Object> userDataMap = new HashMap<>();
                    userDataMap.put("login",login);
                    userDataMap.put("pass",pass);

                    RootRef.child("Users").child(login).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getActivity(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                    {
                        Toast.makeText(getActivity(), "Логин "+ login + " уже зарегистрирован", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void LoginAccount(){
        String login = loginInput.getText().toString();
        String pass = passInput.getText().toString();

        if(TextUtils.isEmpty(login)) {
            Toast.makeText(getActivity(), "Введите логин", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pass)){
            Toast.makeText(getActivity(), "Введите пароль", Toast.LENGTH_SHORT).show();
        }else if(login.length()<5 || login.length()>20){
            Toast.makeText(getActivity(), "Длинна логина от 5 до 20 символов", Toast.LENGTH_SHORT).show();
        }else if(pass.length()<10 || pass.length()>30){
            Toast.makeText(getActivity(), "Длинна пароля от 10 до 30 символов", Toast.LENGTH_SHORT).show();
        }else{
            ValidateAuthorization(login,pass);
        }
    }

    private void ValidateAuthorization(String login,String pass) {
            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance("https://population-census-d9478-default-rtdb.firebaseio.com/").getReference();

            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(parentDbName).child(login).exists())
                    {
                        Users usersData = snapshot.child(parentDbName).child(login).getValue(Users.class);

                        if(usersData.getLogin().equals(login)){
                            if(usersData.getPass().equals(pass)){
                                Toast.makeText(getActivity(), "Успешно", Toast.LENGTH_SHORT).show();
                                Users.isUserAuthorized = true;
                                Users.login_name = login;
                                Users.root = "user";


                                loginInput.setText("");
                                passInput.setText("");

                                Account accountFragment = new Account();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragment_container,accountFragment);
                                transaction.commit();
                            }
                            else{
                                Toast.makeText(getActivity(), "Неверный пароль", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else if(snapshot.child("Admins").child(login).exists()){
                        Users usersData = snapshot.child("Admins").child(login).getValue(Users.class);

                        if(usersData.getLogin().equals(login)){
                            if(usersData.getPass().equals(pass)){
                                Toast.makeText(getActivity(), "Успешно,вы вошли как администратор", Toast.LENGTH_SHORT).show();
                                Users.isUserAuthorized = true;
                                Users.login_name = login;
                                Users.root = "admin";


                                loginInput.setText("");
                                passInput.setText("");

                                Account accountFragment = new Account();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragment_container,accountFragment);
                                transaction.commit();
                            }
                            else{
                                Toast.makeText(getActivity(), "Неверный пароль", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else{
                        Toast.makeText(getActivity(), "Аккаунт не существует", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull  DatabaseError error) {
                    Toast.makeText(getActivity(), "Ошибка подключения", Toast.LENGTH_SHORT).show();
                }
            });
    }

}


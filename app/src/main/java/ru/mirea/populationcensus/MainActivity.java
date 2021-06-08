package ru.mirea.populationcensus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.mirea.populationcensus.Model.Users;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.homeId);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
    }


    Home homeFragment = new Home();
    Search searchFragment = new Search();
    About aboutFragment = new About();
    Profile profileFragment = new Profile();
    Account accountFragment = new Account();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.aboutId:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,aboutFragment).commit();
                return true;
            case R.id.homeId:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
                return true;
            case R.id.searchId:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit();
                return true;
            case R.id.profileId:
                if(Users.isUserAuthorized == false) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                    return true;
                }else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,accountFragment ).commit();
                    return true;
                }
        }
        return false;
    }
}

package com.example.intlok;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.intlok.fragments.menu_fragment;
import com.example.intlok.fragments.perfil_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class activity_main extends AppCompatActivity {

    BottomNavigationView navegacion;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        navegacion = (BottomNavigationView) findViewById(R.id.nav_view);
        navegacion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.navigation_home){
                    showSelectedFragment(new menu_fragment());
                }
                if(item.getItemId()==R.id.navigation_dashboard){

                }
                if(item.getItemId()==R.id.navigation_notifications){
                    showSelectedFragment(new perfil_fragment());
                }

                return true;
            }
        });
    }

    public void showSelectedFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}

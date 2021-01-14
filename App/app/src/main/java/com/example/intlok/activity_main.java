package com.example.intlok;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.intlok.api.Constans;
import com.example.intlok.fragments.menu_fragment;
import com.example.intlok.fragments.perfil_fragment;
import com.example.intlok.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class activity_main extends AppCompatActivity {

    private static final int GALLERY_ADD_PROFILE = 1;
    private Bitmap bitmap = null;

    TextView seguidores;
    View vista;
    String token;
    BottomNavigationView navegacion;

    private FragmentManager fragmentManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        token = i.getStringExtra("token");
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameHomeContainer, new HomeFragment()).commit();



        seguidores= (TextView) findViewById(R.id.textViewSeg);
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
                    showSelectedFragment(new perfil_fragment(Constans.AUTHTOKEN));
                }

                return true;
            }
        });
    }
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode==GALLERY_ADD_PROFILE && resultCode==RESULT_OK){
            Uri imguro= data.getData();

        }
    }*/

    public void showSelectedFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}

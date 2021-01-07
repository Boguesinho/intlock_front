package com.example.intlok;

import android.app.ActivityOptions;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.example.intlok.api.ApiClient;
import com.example.intlok.api.Constans;
import com.example.intlok.models.LoginRequest;
import com.example.intlok.models.LoginResponse;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_loginmain extends AppCompatActivity {
    EditText usuario,password;
    Button btningresar,btnRegistrar;

    public static final long DURATION_TRANSITION=1000;
    boolean band;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario=(EditText)findViewById(R.id.editText_usuarioLogin);
        password=(EditText)findViewById(R.id.editText_Password);

        btningresar=(Button)findViewById(R.id.btn_IngresarLogin);

        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                while (band==false) {

                    if(usuario.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                        Toast.makeText(activity_loginmain.this, "Campos vacios", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else{
                        if(usuario.getText().toString().isEmpty()){
                            Toast.makeText(activity_loginmain.this, "Campo usuario vacio", Toast.LENGTH_LONG).show(); return;
                        }
                        if(password.getText().toString().isEmpty()){
                            Toast.makeText(activity_loginmain.this, "Campo contraseña vacio", Toast.LENGTH_LONG).show();return;
                        }
                    }
                    band=true;
                }
                if(band){

                    checkLogin(v);
                }
            }
        });

        btnRegistrar=(Button)findViewById(R.id.btn_Registrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onFadeClicked(v,false);
            }
        });
    }

    private static String token;

    public void checkLogin(View v){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(usuario.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userlogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    token=response.body().getToken();
                    Constans.AUTHTOKEN= "Bearer " + token;
                    System.out.println("TOKEN ENVIADO: " + Constans.AUTHTOKEN);
                    Toast.makeText(activity_loginmain.this, "Exito", Toast.LENGTH_LONG).show();
                    onFadeClicked(v,true);
                }else{

                    Toast.makeText(activity_loginmain.this, "Usuario no encontrado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Toast.makeText(activity_loginmain.this, "Throwable", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Transition transition;

    public void onExplodeClicked(View view, boolean band){
        transition= new Explode();
        iniciarActividadSecuandaria(band);
    }

    public void onSlideClicked(View view, boolean band){
        transition= new Slide(Gravity.START);
        iniciarActividadSecuandaria(band);
    }

    public void onFadeClicked(View view, boolean band){
        transition= new Fade(Fade.OUT);
        iniciarActividadSecuandaria(band);
    }

    public void iniciarActividadSecuandaria(boolean band){

        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        if(band){

            Intent siguiente = new Intent(this, activity_main.class);
            siguiente.putExtra("token",token);
            startActivity(siguiente, ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this).toBundle());
        }else{

            Intent siguiente = new Intent(this, activity_registromain.class);
            startActivity(siguiente, ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this).toBundle());
        }
    }
}

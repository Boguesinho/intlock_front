package com.example.intlok;

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
import com.example.intlok.models.RegisterAccountRequest;
import com.example.intlok.models.RegisterAccountResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_registrocontinuacion extends AppCompatActivity {
    EditText nombre, apellidos, email, telefono, genero, informacion;
    Button btnRegistrar;
    String token;

    public static final long DURATION_TRANSITION=1000;
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        int idUsuario = i.getIntExtra("idUsuario", 0);
        token = i.getStringExtra("token");
        id=idUsuario;

        Fade fadein= new Fade(Fade.IN);
        fadein.setDuration(activity_loginmain.DURATION_TRANSITION);
        fadein.setInterpolator(new DecelerateInterpolator());
        getWindow().setEnterTransition(fadein);



        setContentView(R.layout.activity_registrocontinuacion);

        nombre=(EditText)findViewById(R.id.txtBlock_nombre);
        apellidos=(EditText)findViewById(R.id.txtBlock_apellidos);
        email=(EditText)findViewById(R.id.txtBlock_email);
        genero=(EditText)findViewById(R.id.txtBlock_genero);
        informacion=(EditText)findViewById(R.id.txtBlock_informacion);
        telefono=(EditText)findViewById(R.id.txtBlock_telefono);

        btnRegistrar=(Button)findViewById(R.id.btn_Registrar2);

        //terminos=(CheckBox)findViewById(R.id.checkBox_terminos);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty() && email.getText().toString().isEmpty() && genero.getText().toString().isEmpty() && informacion.getText().toString().isEmpty() && telefono.getText().toString().isEmpty()){
                    Toast.makeText(activity_registrocontinuacion.this, "Llene los campos", Toast.LENGTH_LONG).show(); return;
                }
                else{
                    if(nombre.getText().toString().isEmpty()||apellidos.getText().toString().isEmpty()||email.getText().toString().isEmpty() || genero.getText().toString().isEmpty()||informacion.getText().toString().isEmpty()||telefono.getText().toString().isEmpty()){
                        Toast.makeText(activity_registrocontinuacion.this, "Un campo está vacio", Toast.LENGTH_LONG).show(); return;
                    }

                    createAccount(v);

                }
            }
        });

    }

    public void createAccount(View v){
        RegisterAccountRequest registerAccountRequest = new RegisterAccountRequest();
        registerAccountRequest.setIdUsuario(id);
        registerAccountRequest.setNombre(nombre.getText().toString());
        registerAccountRequest.setApellidos(apellidos.getText().toString());
        registerAccountRequest.setEmail(email.getText().toString());
        registerAccountRequest.setTelefono(telefono.getText().toString());
        registerAccountRequest.setGenero(genero.getText().toString());
        registerAccountRequest.setInfo(informacion.getText().toString());

        //registerAccountRequest.setToken(token);

        Call<RegisterAccountResponse> registerAccountResponseCall = ApiClient.getUserService().createAccount(registerAccountRequest,"Bearer "+token);
        registerAccountResponseCall.enqueue(new Callback<RegisterAccountResponse>() {
            @Override
            public void onResponse(Call<RegisterAccountResponse> call, Response<RegisterAccountResponse> response) {
                onFadeClicked(v);
            }

            @Override
            public void onFailure(Call<RegisterAccountResponse> call, Throwable t) {
                Toast.makeText(activity_registrocontinuacion.this, "No se puedo crear la cuenta", Toast.LENGTH_LONG).show();
            }
        });
    }


    private Transition transition;

    public void onExplodeClicked(View view){
        transition= new Explode();
        iniciarActividadSecuandaria();
    }

    public void onSlideClicked(View view){
        transition= new Slide(Gravity.START);
        iniciarActividadSecuandaria();
    }

    public void onFadeClicked(View view){
        transition= new Fade(Fade.OUT);
        iniciarActividadSecuandaria();
    }

    public void iniciarActividadSecuandaria(){

        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);


        Intent siguiente = new Intent(this, activity_main.class);
        siguiente.putExtra("idUsuario", id);
        startActivity(siguiente, ActivityOptionsCompat
                .makeSceneTransitionAnimation(this).toBundle());
    }
}

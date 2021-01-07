package com.example.intlok.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.intlok.api.Constans;
import com.example.intlok.R;
import com.example.intlok.api.ApiClient;
import com.example.intlok.models.Cuenta;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class perfil_fragment extends Fragment {

    private String token;
    private TextView textoSeguidores;
    private TextView textoSeguidos;
    private TextView textoPosts;
    private TextView textoNombre;
    private TextView textoEstado;
    private Button btnImagen;
    private View view;

    public perfil_fragment(String _token){
        token = _token;
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_perfil, container,false);

        textoSeguidores = (TextView) view.findViewById(R.id.txtCantidadSeguidores);
        textoSeguidos = (TextView) view.findViewById(R.id.txtCantidadSeguidos);
        textoPosts = (TextView) view.findViewById(R.id.txtCantidadPosts);
        textoNombre = (TextView) view.findViewById(R.id.txtNombrePerfil);
        textoEstado = (TextView) view.findViewById(R.id.txtEstado);

        btnImagen = (Button)  view.findViewById(R.id.btnImagenPerfil);
        btnImagen.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        cantidadSeguidores();
        cantidadSeguidos();
        cantidadPosts();
        usuarioYEstado();

        return view;
    }

    //PROCESO DE CAMBIO DE LA IMAGEN DEL USUARIO

    public void abrirGaleria() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            System.out.println("IMAGEN SELECCIONADA");
        }
    }

    //OBTENER Y COLOCAR CANTIDAD DE SEGUIDORES

    public void cantidadSeguidores(){
        Call<Integer> conteoSeguidores = ApiClient.getUserService().getFollowers(Constans.AUTHTOKEN);
        conteoSeguidores.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                textoSeguidores.setText(response.body().toString());
                view.invalidate();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                textoSeguidos.setText("-");
                view.invalidate();
            }
        });
    }

    //OBTENER Y COLOCAR CANTIDAD DE SEGUIDOS

    public void cantidadSeguidos(){
        Call<Integer> conteoSeguidos = ApiClient.getUserService().getSeguidos(Constans.AUTHTOKEN);
        conteoSeguidos.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                textoSeguidos.setText(response.body().toString());
                view.invalidate();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                textoSeguidos.setText("-");
                view.invalidate();
            }
        });
    }

    //OBTENER Y COLOCAR CANTIDAD DE POSTS

    public void cantidadPosts(){
        Call<Integer> conteoPosts = ApiClient.getUserService().getPostsCount(Constans.AUTHTOKEN);
        conteoPosts.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                textoPosts.setText(response.body().toString());
                view.invalidate();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                textoSeguidos.setText("-");
                view.invalidate();
            }
        });
    }

    //OBTENER Y COLOCAR NOMBRE DEL USUARIO

    public void usuarioYEstado(){

        Call<Cuenta> cuentaCall = ApiClient.getUserService().getCuenta(Constans.AUTHTOKEN);
        cuentaCall.enqueue(new Callback<Cuenta>() {
            @Override
            public void onResponse(Call<Cuenta> call, Response<Cuenta> response) {

                Cuenta cuenta = response.body();

                textoNombre.setText(cuenta.getNombre() + " " +  cuenta.getApaellidos());
                textoEstado.setText(cuenta.getInfo());
                view.invalidate();

            }

            @Override
            public void onFailure(Call<Cuenta> call, Throwable t) {
                textoNombre.setText("-");
                textoEstado.setText("-");
                view.invalidate();
            }
        });
    }
}

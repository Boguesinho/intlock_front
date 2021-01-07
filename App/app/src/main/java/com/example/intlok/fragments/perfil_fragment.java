package com.example.intlok.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.intlok.MainActivity;
import com.example.intlok.activity_registrocontinuacion;
import com.example.intlok.activity_registromain;
import com.example.intlok.api.Constans;
import com.example.intlok.R;
import com.example.intlok.api.ApiClient;
import com.example.intlok.models.Cuenta;
import com.example.intlok.models.ImagenPerfilRequest;
import com.example.intlok.models.RegisterAccountRequest;
import com.example.intlok.models.RegisterAccountResponse;
import com.example.intlok.models.RegisterUserRequest;
import com.example.intlok.models.RegisterUserResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    private int idUsuario;

    public perfil_fragment(String _token){
        token = _token;
    }
    int STORAGE_PERMISSION_CODE = 1;




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
        if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 1);
        } else {
            requestStoragePermission();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uriImagen = data.getData();
            System.out.println("ENVIANDO: " + getRealPathFromURI(uriImagen));

            actualizarFoto(uriImagen);
        }
    }

    private void actualizarFoto (Uri uriImagen) {
        if (uriImagen != null){
            System.out.println("URI NO ES NULL");
            File imageFile = new File(getRealPathFromURI(uriImagen));
            RequestBody requestBody = RequestBody.create(imageFile, MediaType.parse("images/jpeg"));
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("ruta", imageFile.getName(), requestBody);

            Call<ResponseBody> subirFotoCall = ApiClient.getUserService().subirFotoPerfil(Constans.AUTHTOKEN, filePart);
            System.out.println("Entrando a callback");
            subirFotoCall.enqueue(new Callback<ResponseBody>() {


                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println("DENTRO DE CALLBACK");
                    if(response.isSuccessful()) {
                        System.out.println("Guardado correcto");

                    } else {
                        System.out.println("Guardado incorrecto");

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println(t.getMessage());
                }

            });
        } else {
            System.out.println("uri es null");
        }
    }

    public void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(),"PERMISSION GRANTED",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(),"PERMISSION DENIED",Toast.LENGTH_LONG).show();
            }
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

    public String getRealPathFromURI(Uri uri) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}

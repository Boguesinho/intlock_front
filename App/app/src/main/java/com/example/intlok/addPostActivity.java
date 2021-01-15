package com.example.intlok;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.intlok.api.Constans;
import com.example.intlok.models.Post;
import com.example.intlok.fragments.menu_fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class addPostActivity extends AppCompatActivity {

    private Button btnPost;
    private ImageView imgPost;
    private EditText txtDesc;
    private Bitmap bitmap = null;
    private static final  int GALLERY_CHANGE_POST = 3;
    private ProgressDialog dialog;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        init();
    }


    private void init() {
        preferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        btnPost = findViewById(R.id.btnAddPost);
        imgPost = findViewById(R.id.imgAddPost);
        txtDesc = findViewById(R.id.txtDescAddPost);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        imgPost.setImageURI(getIntent().getData());
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),getIntent().getData());
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnPost.setOnClickListener(v->{
            if(!txtDesc.getText().toString().isEmpty()){
                //post();
            }else {
                Toast.makeText(this, "Post description is required", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
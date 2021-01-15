package com.example.intlok.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.intlok.R;
import com.example.intlok.adapters.PostsAdapter;
import com.example.intlok.api.ApiClient;
import com.example.intlok.api.Constans;
import com.example.intlok.api.InterfaceAPI;
import com.example.intlok.models.ImagenPostResponse;
import com.example.intlok.models.MultimediaResponse;
import com.example.intlok.models.Post;
import com.example.intlok.activity_main;
import com.example.intlok.models.User;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menu_fragment extends Fragment {
    private View view;
    public static RecyclerView recyclerView;
    public static ArrayList<Post> postArrayList;
    private SwipeRefreshLayout refreshLayout;
    private PostsAdapter postsAdapter;
    private MaterialToolbar toolbar;



    public menu_fragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_home,container,false);
        init();
        return view;
    }

    private void init(){
        recyclerView = view.findViewById(R.id.recyclerHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout = view.findViewById(R.id.swipeHome);
        toolbar= view.findViewById(R.id.toolbarHome);
        ((activity_main)getContext()).setSupportActionBar(toolbar);

        getPosts();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPosts();
            }
        });
    }

    private void getImages(){

    }

    private void getPosts(){

        postArrayList = new ArrayList<>();
        refreshLayout.setRefreshing(true);

        StringRequest request= new StringRequest(Request.Method.GET, Constans.POSTS, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if(object!=null){
                    JSONArray array = new JSONArray(object.getString("posts"));
                    for(int i=0; i<array.length();i++){
                        JSONObject postObject = array.getJSONObject(i);
                        //JSONObject userObject = postObject.getJSONObject("user");

                        Post post = new Post();
                        post.setId(postObject.getInt("id"));
                        post.setIdUsuario(postObject.getInt("idUsuario"));
                        post.setIdMultimedia(postObject.getInt("idMultimedia"));
                        post.setDescripcion(postObject.getString("descripcion"));
                        post.setCreated(postObject.getString("created_at"));

                        Call<ImagenPostResponse> imagenPostResponseCall = ApiClient.getUserService().getImagenesPost(post.getIdMultimedia(),"Bearer "+Constans.AUTHTOKEN);
                        imagenPostResponseCall.enqueue(new Callback<ImagenPostResponse>() {
                            @Override
                            public void onResponse(Call<ImagenPostResponse> call, Response<ImagenPostResponse> response) {
                                if(response.isSuccessful()){
                                    post.setRutaPost(response.body().getRuta());
                                }
                            }

                            @Override
                            public void onFailure(Call<ImagenPostResponse> call, Throwable t) {

                            }
                        });

                        Call<MultimediaResponse> multimediaResponseCall = ApiClient.getUserService().getImagenPerfil("Bearer "+Constans.AUTHTOKEN);
                        multimediaResponseCall.enqueue(new Callback<MultimediaResponse>() {
                            @Override
                            public void onResponse(Call<MultimediaResponse> call, Response<MultimediaResponse> response) {
                                post.setRutaPerfil(response.body().getRuta());
                            }

                            @Override
                            public void onFailure(Call<MultimediaResponse> call, Throwable t) {

                            }
                        });



                        postArrayList.add(post);
                    }
                    postsAdapter= new PostsAdapter(getContext(),postArrayList);
                    recyclerView.setAdapter(postsAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            refreshLayout.setRefreshing(false);

        },error -> {
            error.printStackTrace();;
            refreshLayout.setRefreshing(false);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer "+Constans.AUTHTOKEN);
                return super.getHeaders();
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }
}

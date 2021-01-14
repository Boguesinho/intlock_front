package com.example.intlok.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.intlok.R;
import com.example.intlok.adapters.PostsAdapter;
import com.example.intlok.api.ApiClient;
import com.example.intlok.models.Post;
import com.example.intlok.activity_main;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menu_fragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Post> postArrayList;
    private SwipeRefreshLayout refreshLayout;
    private PostsAdapter postsAdapter;
    private MaterialToolbar toolbar;
    private String token;



    public menu_fragment(String token){
        this.token=token;
    }

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

    private void getPosts(){
        postArrayList = new ArrayList<>();
        refreshLayout.setRefreshing(true);


        Call<List<Post>> postCall= ApiClient.getUserService().getPosts(token);
        postCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    List<Post> list= response.body();

                    for (Post i : list
                         ) {
                        postArrayList.add(i);
                    }
                    postsAdapter = new PostsAdapter(getContext(),postArrayList);
                    recyclerView.setAdapter(postsAdapter);
                }


                refreshLayout.setRefreshing(false);
            }


            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                refreshLayout.setRefreshing(false);
            }
        });

    }
}

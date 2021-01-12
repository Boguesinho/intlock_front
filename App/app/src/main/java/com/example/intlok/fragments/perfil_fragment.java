package com.example.intlok.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.intlok.R;
import com.example.intlok.api.ApiClient;
import com.example.intlok.models.Follower;
import com.example.intlok.models.FollowerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class perfil_fragment extends Fragment {

    public static int auxcount;
    public perfil_fragment(){
        countFollowers();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_notifications, container,false);

    }

    public void countFollowers(){
        Call<List<FollowerResponse>> listCall = ApiClient.getUserService().getFollowers();
        listCall.enqueue(new Callback<List<FollowerResponse>>() {
            @Override
            public void onResponse(Call<List<FollowerResponse>> call, Response<List<FollowerResponse>> response) {
                int count=0;
                if(!response.isSuccessful()){

                }
                List<FollowerResponse> list = response.body();


                for (FollowerResponse i : list
                     ) {
                    System.out.println("Hola");
                    count++;
                }
                auxcount=count;
            }

            @Override
            public void onFailure(Call<List<FollowerResponse>> call, Throwable t) {

            }
        });
        System.out.println(auxcount);

    }
}

package com.example.intlok.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.intlok.R;

import com.example.intlok.api.Constans;
import com.example.intlok.models.Post;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsHolder> {

    private Context context;
    private ArrayList<Post> postArrayList;

    public PostsAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public PostsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_post,parent,false);
        return new PostsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsHolder holder, int position) {
        Post post = postArrayList.get(position);
        Picasso.get().load(Constans.URL+"public/imagen"+post.getRutaPerfil()).into(holder.imgProfile);
        Picasso.get().load(Constans.URL+"public/imagen"+post.getRutaPost()).into(holder.imgPost);
        holder.txtLikes.setText("20");
        holder.txtComments.setText("20");
        holder.txtDesc.setText(post.getDescripcion());
        holder.txtDate.setText(post.getCreated());
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    class PostsHolder extends RecyclerView.ViewHolder{
        private TextView textName,txtDate,txtDesc,txtLikes,txtComments;
        private CircularImageView imgProfile;
        private ImageView imgPost;
        private ImageButton btnPostOption, btnLike, btnComment;

        public PostsHolder(@NonNull View itemView){
            super(itemView);
            textName = itemView.findViewById(R.id.txtPostName);
            txtDate = itemView.findViewById(R.id.txtPostDate);
            txtDesc = itemView.findViewById(R.id.txtPostDesc);
            txtLikes = itemView.findViewById(R.id.txtPostLikes);
            txtComments = itemView.findViewById(R.id.txtPostComments);
            imgProfile = itemView.findViewById(R.id.imgPostProfile);
            imgPost = itemView.findViewById(R.id.imgPostPhoto);
            btnPostOption = itemView.findViewById(R.id.btnPostOption);
            btnLike = itemView.findViewById(R.id.btnPostLike);
            btnComment = itemView.findViewById(R.id.btnPostComment);

        }
    }
}

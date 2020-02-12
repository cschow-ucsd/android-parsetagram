package com.example.android_parsetagram;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {
    private List<Post> posts;

    public PostsAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView handle, description;
        private ImageView image;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            handle = itemView.findViewById(R.id.tvHandle);
            description = itemView.findViewById(R.id.tvDescrption);
            image = itemView.findViewById(R.id.tvImage);
        }

        public void bind(Post post) {
            handle.setText(post.getUser().getUsername());
            description.setText(post.getDescription());
            ParseFile imageFile = post.getImage();
            if (imageFile != null) {
                Glide.with(itemView.getContext()).load(imageFile.getUrl()).into(image);
            }
        }
    }
}

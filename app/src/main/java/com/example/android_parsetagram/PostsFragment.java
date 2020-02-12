package com.example.android_parsetagram;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {
    private static final String TAG = "PostsFragment";
    private RecyclerView rvPosts;
    protected List<Post> posts = new ArrayList<>();
    protected PostsAdapter adapter;
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPosts = view.findViewById(R.id.rvPosts);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostsAdapter(posts);
        rvPosts.setAdapter(adapter);

        swipeRefreshLayout = view.findViewById(R.id.postsRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts();
            }
        });

        queryPosts();
    }

    protected void queryPosts() {
        final ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "done: Query failed.", e);
                    return;
                }
                swipeRefreshLayout.setRefreshing(false);
                posts.clear();
                posts.addAll(objects);
                adapter.notifyDataSetChanged();
                for (Post object : objects) {
                    Log.d(TAG, "done: " + object.getDescription() + "; " + object.getUser().getUsername());
                }
            }
        });
    }
}

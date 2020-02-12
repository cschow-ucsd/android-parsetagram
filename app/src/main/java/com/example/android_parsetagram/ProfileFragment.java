package com.example.android_parsetagram;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {
    private static final String TAG = "ProfileFragment";

    @Override
    protected void queryPosts() {
        final ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
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

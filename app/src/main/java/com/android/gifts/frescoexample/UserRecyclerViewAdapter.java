package com.android.gifts.frescoexample;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.Collections;
import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {
    List<User> users = Collections.emptyList();
    LayoutInflater layoutInflater;
    Context context;

    public UserRecyclerViewAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = layoutInflater.inflate(R.layout.single_user_row, parent, false);
        UserViewHolder holder = new UserViewHolder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User thisUser = users.get(position);

        holder.userName.setText(thisUser.name);

        // parse the URL
        final Uri uri = Uri.parse(thisUser.image);

        // Listen to Download events
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable anim) {
                // Image Loaded

                // Check if image loaded from cache or not.
                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                boolean inMemoryCache = imagePipeline.isInBitmapMemoryCache(uri);

                Toast.makeText(context, "Image Loaded, Cached ? " + inMemoryCache, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                // Failure happened
                Toast.makeText(context, "Error loading: " + id, Toast.LENGTH_SHORT).show();
            }
        };

        // Initialize a controller and attach the listener to it.
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setControllerListener(controllerListener)
                .build();

        holder.userImage.setController(controller);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private SimpleDraweeView userImage;

        public UserViewHolder(View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.user_name);
            userImage = (SimpleDraweeView) itemView.findViewById(R.id.user_image);
        }
    }
}

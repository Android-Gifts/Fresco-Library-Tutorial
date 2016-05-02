package com.android.gifts.frescoexample;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

public class GIFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        SimpleDraweeView gif = (SimpleDraweeView) findViewById(R.id.gif);

        // GIF Uri
        Uri uri = Uri.parse("http://p.fod4.com/p/media/22e39b6ac0/T58DAqfzR3iXMrL0EzkK_b4.gif");

        // Listen to Download events
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable anim) {
                // Image Loaded
                Toast.makeText(getApplicationContext(), "Image Loaded: " + id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                // Failure happened
                Toast.makeText(getApplicationContext(), "Error loading: " + id, Toast.LENGTH_SHORT).show();
            }
        };

        // Initialize a controller and attach the listener to it.
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setControllerListener(controllerListener)
                .setAutoPlayAnimations(true)
                .build();

        gif.setController(controller);
    }
}
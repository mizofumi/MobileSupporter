package net.mizofumi.mobilesupporter.Image;

import android.content.Context;
import android.graphics.Bitmap;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


/**
 * Created by mizofumi on 16/08/17.
 */
public class ImageDownload {
    SimpleTarget<Bitmap> simpleTarget;
    String url;
    ImageDownloadListener listener;

    public ImageDownload(String url, final ImageDownloadListener listener) {
        this.url = url;
        this.listener = listener;
        this.simpleTarget = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                listener.onPostExec(resource);
            }
        };
    }

    public void download(Context context){
        Glide.with(context).load(url).asBitmap().into(simpleTarget);
    }

}

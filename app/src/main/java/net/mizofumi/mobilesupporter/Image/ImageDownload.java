package net.mizofumi.mobilesupporter.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mizofumi on 16/08/17.
 */
public class ImageDownload {
    Target target;
    String url;
    ImageDownloadListener listener;

    public ImageDownload(String url, final ImageDownloadListener listener) {
        this.url = url;
        this.listener = listener;
        this.target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                listener.onPostExec(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                listener.error(new Exception("Image Download Error"));
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
    }

    public void download(Context context){
        Picasso.with(context).load(url).into(target);
    }

}

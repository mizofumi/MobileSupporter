package net.mizofumi.mobilesupporter.Image;

import android.graphics.Bitmap;

import java.io.InputStream;

/**
 * Created by mizofumi on 16/08/17.
 */
public interface ImageDownloadListener{
    void onPostExec(Bitmap bitmap);
}

package net.mizofumi.mobilesupporter.Image;

import java.io.IOException;

/**
 * Created by mizofumi on 16/08/17.
 */
public interface GoogleImageSearchListener {
    void onPreExec();
    void onPostExec(String imageUrl);
    void onBackground();
    void error(Exception e);
}

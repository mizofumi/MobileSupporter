package net.mizofumi.mobilesupporter.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mizofumi on 16/08/17.
 */
public class ImageUtils {
    public static byte[] readAll(InputStream stream) throws IOException{
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte [] buffer = new byte[1024];
        while(true) {
            int len = stream.read(buffer);
            if(len < 0) {
                break;
            }
            bout.write(buffer, 0, len);
        }
        return bout.toByteArray();
    }
}

package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;

/**
 * Created by sergey on 9/26/15.
 */
public interface ImageProvider {
    boolean isImagePresent();
    Bitmap getImage() throws ImageNotLoadedException;
}

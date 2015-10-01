package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

/**
 * Created by sergey on 9/30/15.
 */
public interface ImagesStorageFactory {
    ImageProvider createImageProvider();
    ImageSaver createImageSaver();
}

package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

import java.io.File;

public class FilesImagesStorageFactory implements ImagesStorageFactory {

    private ImageFileNameProvider mImageFileNameProvider;

    public FilesImagesStorageFactory(File destinationDirectory){
        if (destinationDirectory == null) {
            throw new IllegalArgumentException("destinationDirectory");
        }
        if (!destinationDirectory.exists()) {
            throw new IllegalArgumentException("destination directory not exists");
        }
        mImageFileNameProvider = new ImageFileNameProvider(destinationDirectory);
    }

    @Override
    public ImageProvider createImageProvider() {
        return new FilesImageProvider(mImageFileNameProvider);
    }

    @Override
    public ImageSaver createImageSaver() {
        return new FilesImageSaver(mImageFileNameProvider);
    }
}

package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Direction;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sergey on 9/26/15.
 */
public class FilesImageSaver implements ImageSaver {

    private static final String TAG = "FilesImageSaver";

    private ImageFileNameProvider mFileNameProvider;

    public FilesImageSaver(ImageFileNameProvider fileNameProvider) {
        if (fileNameProvider == null) {
            mFileNameProvider = fileNameProvider;
        }

        mFileNameProvider = fileNameProvider;
    }

    @Override
    public void saveImage(File imageTempFile, RoomCoordinates coordinates, Direction direction, SaveImageCallback callback) {
        File imageFile = mFileNameProvider.getImageFile(coordinates, direction);

        new SaveTask(imageFile, imageTempFile, callback).execute();
    }

    private class SaveTask extends AsyncTask<Void, Void, Void> {

        private File mImageFile;
        private File mImageTempFile;
        private SaveImageCallback mCallback;

        public SaveTask(File imageFile, File imageTempFile, SaveImageCallback callback) {
            if (imageFile == null) {
                throw new IllegalArgumentException("imageFile");
            }
            if (imageTempFile == null) {
                throw new IllegalArgumentException("imageTempFile");
            }
            if (!imageTempFile.exists()) {
                throw new IllegalArgumentException("image file not exists");
            }
            if (callback == null) {
                throw new IllegalArgumentException("callback");
            }

            mImageFile = imageFile;
            mImageTempFile = imageTempFile;
            mCallback = callback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (mImageFile.exists() && !mImageFile.delete()) {
                //FIXME throw exception or something like this
            }

            try {
                copy(mImageTempFile, mImageFile);
            } catch (IOException e) {
                Log.e(TAG, "error copy file.", e);
                cancel(false);
            }

            return null;
        }

        @Override
        protected void onCancelled() {
            mCallback.notSaved();
        }

        @Override
        protected void onPostExecute(Void result) {
            mCallback.success();
        }

        public void copy(File src, File dst) throws IOException {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);

            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                in.close();
                out.close();
            }
        }
    }
}

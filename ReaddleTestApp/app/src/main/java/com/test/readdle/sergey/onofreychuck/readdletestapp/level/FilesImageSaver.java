package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public void saveImage(Bitmap image, RoomCoordinates coordinates, Direction direction, SaveImageCallback callback) {
        File imageFile = mFileNameProvider.getImageFile(coordinates, direction);

        new SaveTask(imageFile, image, callback).execute();
    }

    private class SaveTask extends AsyncTask<Void, Void, Void> {

        private File mImageFile;
        private Bitmap mImage;
        private SaveImageCallback mCallback;

        public SaveTask(File imageFile, Bitmap image, SaveImageCallback callback) {
            if (imageFile == null) {
                throw new IllegalArgumentException("imageFile");
            }
            if (image == null) {
                throw new IllegalArgumentException("image");
            }
            if (callback == null) {
                throw new IllegalArgumentException("callback");
            }

            mImageFile = imageFile;
            mImage = image;
            mCallback = callback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (mImageFile.exists() && !mImageFile.delete()) {
                //FIXME throw exception or something like this
            }

            try {
                OutputStream out = new FileOutputStream(mImageFile);
                mImage.compress(Bitmap.CompressFormat.JPEG, 85, out); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                out.flush();
                out.close(); // do not forget to close the stream
            } catch (IOException ioe) {
                Log.e(TAG, "exception saving image to file: " + mImageFile.getAbsolutePath(), ioe);
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
    }
}

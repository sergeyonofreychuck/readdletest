package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;

/**
 * Created by sergey on 9/26/15.
 */
public class FilesImageProvider implements ImageProvider {

    private static final String TAG = "FilesImageProvider";

    private ImageFileNameProvider mFileNameProvider;

    public FilesImageProvider(ImageFileNameProvider fileNameProvider) {
        if (fileNameProvider == null) {
            mFileNameProvider = fileNameProvider;
        }

        mFileNameProvider = fileNameProvider;
    }


    @Override
    public void tryGetImage(RoomCoordinates coordinates, Direction direction, GetImageCallback callback) {
        File imageFile = mFileNameProvider.getImageFile(coordinates, direction);

        new LoaderTask(imageFile, callback);
    }

    private class LoaderTask extends AsyncTask<Void, Void, Bitmap> {

        private File mImageFile;
        private GetImageCallback mCallback;

        public LoaderTask(File imageFile, GetImageCallback callback) {
            if (imageFile == null) {
                throw new IllegalArgumentException("imageFile");
            }
            if (callback == null) {
                throw new IllegalArgumentException("callback");
            }

            mImageFile = imageFile;
            mCallback = callback;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            if (!mImageFile.exists()) {
                cancel(false);
            }

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(mImageFile.getAbsolutePath(), options);
            if(bitmap == null) {
                cancel(false);
            }

            return bitmap;
        }

        @Override
        protected void onCancelled() {
            mCallback.notLoaded();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            mCallback.success(result);
        }
    }

}

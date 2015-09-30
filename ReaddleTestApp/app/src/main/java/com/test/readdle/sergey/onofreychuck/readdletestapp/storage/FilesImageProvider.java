package com.test.readdle.sergey.onofreychuck.readdletestapp.storage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Direction;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

import java.io.File;

public class FilesImageProvider implements ImageProvider {

    private static final String TAG = "FilesImageProvider";

    private ImageFileNameProvider mFileNameProvider;

    FilesImageProvider(ImageFileNameProvider fileNameProvider) {
        if (fileNameProvider == null) {
            throw new IllegalArgumentException("fileNameProvider");
        }

        mFileNameProvider = fileNameProvider;
    }


    @Override
    public void tryGetImage(RoomCoordinates coordinates, Direction direction, GetImageCallback callback) {
        Log.d(TAG, "try get image: " + coordinates + "   " + direction);

        File imageFile = mFileNameProvider.getImageFile(coordinates, direction);

        new LoaderTask(imageFile, callback).execute();
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
                return null;
            }

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(mImageFile.getAbsolutePath(), options);
            if(bitmap == null) {
                Log.d(TAG, "bitmap null: " + mImageFile.getAbsolutePath() + " cancel");
                cancel(false);
                return null;
            }

            return bitmap;
        }

        @Override
        protected void onCancelled() {
            Log.d(TAG, "onCancelled");
            mCallback.notLoaded();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            Log.d(TAG, "onPostExecute");
            mCallback.success(result);
        }
    }

}

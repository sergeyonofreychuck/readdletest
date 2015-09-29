package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.ImageSaver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DeviceCamera extends DeviceAbstract {

    private ImageSaver mImageSaver;
    private Fragment mFragment;
    private Map<Integer, File> mImageFiles;
    private ImageSavedCallback mCallback;

    public DeviceCamera(
            Fragment fragment,
            Room currentRoom,
            Direction direction,
            Map<Direction,Bitmap>
            icons, ImageSaver imageSaver,
            ImageSavedCallback callback) {
        super(currentRoom, direction, icons);

        if (imageSaver == null) {
            throw new IllegalArgumentException("imageSaver");
        }

        if (fragment == null) {
            throw new IllegalArgumentException("fragment");
        }

        mImageSaver = imageSaver;
        mFragment = fragment;
        mImageFiles = new HashMap<>();
        mCallback = callback;
    }

    public void makePhoto(){
        dispatchTakePictureIntent();
    }

    public void processActivityForResult(int requestCode, int resultCode) {
        Log.d(TAG, "process activity for result. request code: " + requestCode + " result code: " + resultCode);

        if (mImageFiles.containsKey(requestCode)) {
            if (resultCode == Activity.RESULT_OK) {
                processImage(requestCode);
            }
        } else {
            Log.d(TAG, "image files not contains: " + requestCode);
        }
    }

    private void processImage(final int fileId){
        Log.d(TAG, "process image: " + fileId);

        final File imageTempFile = mImageFiles.get(fileId);
        mImageSaver.saveImage(imageTempFile, getRoom().getCoordinates(), getDirection(), new ImageSaver.SaveImageCallback() {
            @Override
            public void success() {
                if (!mImageFiles.get(fileId).delete()){
                    throw new RuntimeException("cant remove temp file");
                    //TODO add error handling
                }
                mImageFiles.remove(fileId);
                mCallback.imageSaved();
            }

            @Override
            public void notSaved() {
                throw new RuntimeException("error saving image");
                //TODO handle error
            }
        });
    }

    private File createImageFile(int fileId) throws IOException {
        String imageFileName = "TMP_JPEG_" + fileId + "_";
        File storageDir = mFragment.getContext().getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mFragment.getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile;
            int fileId = new Random().nextInt(65535);
            try {
                photoFile = createImageFile(fileId);
                mImageFiles.put(fileId, photoFile);
            } catch (IOException ex) {
                throw new RuntimeException("error create photo file");
                //TODO handle it
            }

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(photoFile));
            mFragment.startActivityForResult(takePictureIntent, fileId);
        }
        //TODO handle when there is no camera on device
    }

    public interface ImageSavedCallback {
        void imageSaved();
    }
}

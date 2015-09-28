package com.test.readdle.sergey.onofreychuck.readdletestapp.level;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.test.readdle.sergey.onofreychuck.readdletestapp.storage.ImageSaver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by sergey on 9/26/15.
 */
public class DeviceCamera extends DeviceAbstract {

    private ImageSaver mImageSaver;
    private Activity mActivity;
    private Map<Integer, File> mImageFiles;

    public DeviceCamera(Activity activity, Room currentRoom, Direction direction, Map<Direction,Bitmap> icons, ImageSaver imageSaver) {
        super(currentRoom, direction, icons);

        if (imageSaver == null) {
            throw new IllegalArgumentException("imageSaver");
        }

        if (activity == null) {
            throw new IllegalArgumentException("context");
        }

        mImageSaver = imageSaver;
        mActivity = activity;
        mImageFiles = new HashMap<>();
    }

    public void makePhoto(){
        dispatchTakePictureIntent();
    }

    public void processActivityForResult(int requestCode, int resultCode) {
        if (mImageFiles.containsKey(requestCode)) {
            if (resultCode == Activity.RESULT_OK) {
                processImage(requestCode);
            }
        }
    }

    private void processImage(final int fileId){
        final File imageTempFile = mImageFiles.get(fileId);
        mImageSaver.saveImage(imageTempFile, mRoom.getCoordinates(), mDirection, new ImageSaver.SaveImageCallback() {
            @Override
            public void success() {
                mImageFiles.get(fileId).delete();
                mImageFiles.remove(fileId);
            }

            @Override
            public void notSaved() {
                //TODO do something
            }
        });
    }

    private File createImageFile(int fileId) throws IOException {
        String imageFileName = "TMP_JPEG_" + fileId + "_";
        File storageDir = mActivity.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            int fileId = new Random().nextInt();
            try {
                photoFile = createImageFile(fileId);
                mImageFiles.put(fileId, photoFile);
            } catch (IOException ex) {
                //TODO handle it
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                mActivity.startActivityForResult(takePictureIntent, fileId);
            } else {
                //TODO handle it
            }
        }
    }
}

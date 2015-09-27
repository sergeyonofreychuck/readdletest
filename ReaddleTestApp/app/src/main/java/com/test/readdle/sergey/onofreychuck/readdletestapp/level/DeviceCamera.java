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
import java.util.Map;

/**
 * Created by sergey on 9/26/15.
 */
public class DeviceCamera extends DeviceAbstract {

    private ImageSaver mImageSaver;
    private Activity mActivity;
    String mCurrentPhotoPath;

    public DeviceCamera(Activity context, Room currentRoom, Direction direction, Map<Direction,Bitmap> icons, ImageSaver imageSaver) {
        super(currentRoom, direction, icons);

        if (imageSaver == null) {
            throw new IllegalArgumentException("imageSaver");
        }

        if (context == null) {
            throw new IllegalArgumentException("context");
        }

        mImageSaver = imageSaver;
        mActivity = context;
    }

    public void makePhoto(){
        dispatchTakePictureIntent();
    }

    public void processActivityForResult(int requestCode, int resultCode) {

    }

    public void processImage(final File imageTempFile){
        mImageSaver.saveImage(imageTempFile, mRoom.getCoordinates(), mDirection, new ImageSaver.SaveImageCallback() {
            @Override
            public void success() {
                //TODO do something
            }

            @Override
            public void notSaved() {
                //TODO do something
            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                //TODO handle it
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                mActivity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}

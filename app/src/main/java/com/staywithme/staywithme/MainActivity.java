package com.staywithme.staywithme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;
import android.graphics.Matrix;

import com.staywithme.staywithme.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private static final int ACTION_TAKE_PHOTO = 1;
    private static final int ACTION_TAKE_VIDEO = 2;
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private ImageView mImageView;
    private Bitmap mImageBitmap;

    private static final String VIDEO_STORAGE_KEY = "viewvideo";
    private static final String VIDEOVIEW_VISIBILITY_STORAGE_KEY = "videoviewvisibility";
    private VideoView mVideoView;
    private Uri mVideoUri;

    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    /* Photo album for this application */
    private String getAlbumName() {
        return getString(R.string.album_name);
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("Camera", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private void setPic() {

        /* There isn't enough memory to open up more than a couple camera photos */
        /* So pre-scale the target bitmap into which the file is decoded */

        /* Get the size of the ImageView */
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        /* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        /* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

        /* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        /* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        bitmap = rotateBitmap(bitmap, 90);

        /* Associate the Bitmap to the ImageView, make sure the VideoView
         * is cleared to replace with ImageView */
        mImageView.setImageBitmap(bitmap);
        mVideoUri = null;
        mImageView.setVisibility(View.VISIBLE);
        mVideoView.setVisibility(View.INVISIBLE);
    }

    public Bitmap rotateBitmap(Bitmap source, int angle)
    {
        Matrix matrix = new Matrix();
        matrix.set(matrix);
        matrix.setRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, false);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void dispatchTakePictureIntent(int actionCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch(actionCode) {
            case ACTION_TAKE_PHOTO:
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        } // switch

        startActivityForResult(takePictureIntent, actionCode);
    }
    // Captures video from Android camera component
    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
            mCurrentPhotoPath = null;
        }

    }

    private void handleCameraVideo(Intent intent) {
        mVideoUri = intent.getData();
        mVideoView.setVideoURI(mVideoUri);
        mImageBitmap = null;
        mVideoView.setVisibility(View.VISIBLE);
        mImageView.setVisibility(View.INVISIBLE);
        mVideoView.start();
    }

    Button.OnClickListener mTakePicOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakePictureIntent(ACTION_TAKE_PHOTO);
                }
            };
    Button.OnClickListener mTakeVidOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakeVideoIntent();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.taken_photo);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mImageBitmap = null;
        mVideoUri = null;

        Button photoBtn = (Button) findViewById(R.id.click);
        setBtnListenerOrDisable(
                photoBtn,
                mTakePicOnClickListener,
                MediaStore.ACTION_IMAGE_CAPTURE
        );

        Button videoBtn = (Button) findViewById(R.id.record_video);
        setBtnListenerOrDisable(
                videoBtn,
                mTakeVidOnClickListener,
                MediaStore.ACTION_VIDEO_CAPTURE
        );

        mAlbumStorageDirFactory = new BaseAlbumDirFactory();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    // Intent data is how the photo and video transfer into their views
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_TAKE_PHOTO: {
                if (resultCode == RESULT_OK) {
                    handleBigCameraPhoto();
                }
                break;
            } // ACTION_TAKE_PHOTO

            case ACTION_TAKE_VIDEO: {
                if (resultCode == RESULT_OK) {
                    handleCameraVideo(data);
                }
                break;
            } // ACTION_TAKE_VIDEO
        } // switch
    }

    // Some lifecycle callbacks so that the image can survive orientation change
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
        outState.putParcelable(VIDEO_STORAGE_KEY, mVideoUri);
        outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null) );
        outState.putBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY, (mVideoUri != null) );
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
        mVideoUri = savedInstanceState.getParcelable(VIDEO_STORAGE_KEY);
        mImageView.setImageBitmap(mImageBitmap);
        mImageView.setVisibility(
                savedInstanceState.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ?
                        ImageView.VISIBLE : ImageView.INVISIBLE
        );
        mVideoView.setVideoURI(mVideoUri);
        mVideoView.setVisibility(
                savedInstanceState.getBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY) ?
                        ImageView.VISIBLE : ImageView.INVISIBLE
        );
    }

    /**
     * Indicates whether the specified action can be used as an intent. This
     * method queries the package manager for installed packages that can
     * respond to an intent with the specified action. If no suitable package is
     * found, this method returns false.
     * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
     *
     * @param context The application's environment.
     * @param action The Intent action to check for availability.
     *
     * @return True if an Intent with the specified action can be sent and
     *         responded to, false otherwise.
     */
    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void setBtnListenerOrDisable(
            Button btn,
            Button.OnClickListener onClickListener,
            String intentName
    ) {
        if (isIntentAvailable(this, intentName)) {
            btn.setOnClickListener(onClickListener);
        } else {
            btn.setText(
                    getText(R.string.cannot).toString() + " " + btn.getText());
            btn.setClickable(false);
        }
    }
}
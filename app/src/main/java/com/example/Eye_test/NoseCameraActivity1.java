package com.example.Eye_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class NoseCameraActivity1 extends AppCompatActivity {

    Button btn_cam, btn_gallery, btn_close;
    String path;
    File file_1, file1;
    Bitmap bitmap1, rotatedBitmap1;
    public static Context context_nose_camera1;
    private static final int REQUEST_CAMERA_CODE = 100;
    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nose_camera1);

        if (ContextCompat.checkSelfPermission(NoseCameraActivity1.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NoseCameraActivity1.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA_CODE);
        }

        context_nose_camera1 = this;
        btn_cam = findViewById(R.id.btn_cam);
        btn_gallery = findViewById(R.id.btn_gallery);
        btn_close = findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    File tempDir = getCacheDir();
                    String imageFileName = "original";

                    try {
                        File tempImage = File.createTempFile(imageFileName, ".jpg", tempDir);
                        path = tempImage.getAbsolutePath();
                        photoFile = tempImage;

                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(NoseCameraActivity1.this, getPackageName() + ".fileprovider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, 1);
                    }

                }
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                file1 = new File(path);
                try{

                    bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file1));
                    ((NoseImageActivity1) NoseImageActivity1.context_nose).iv_image1.setImageBitmap(bitmap1);

                    if (bitmap1 != null) {
                        ExifInterface ei = new ExifInterface(path);
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        rotatedBitmap1 = null;
                        switch (orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap1 = rotateImage1(bitmap1, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap1 = rotateImage1(bitmap1, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap1 = rotateImage1(bitmap1, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap1 = bitmap1;

                        }
                        SaveBitmapToFileCache(rotatedBitmap1, getFilesDir() + "/" + "original.jpg");

                        Intent intent = new Intent(NoseCameraActivity1.this, NoseImageActivity2.class);
                        startActivity(intent); }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        }else if(requestCode== REQUEST_CODE && resultCode==RESULT_OK && data!=null) {
            Uri photoUri = data.getData();

            try {

                rotatedBitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(),photoUri);
                SaveBitmapToFileCache(rotatedBitmap1, getFilesDir() + "/" + "original.jpg");
                Intent intent = new Intent(NoseCameraActivity1.this, NoseImageActivity2.class);
                startActivity(intent);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static Bitmap rotateImage1(Bitmap source, float angle){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void SaveBitmapToFileCache(Bitmap bitmap, String strFilePath) {

        file_1 = new File(strFilePath);
        OutputStream out = null;
        try {
            file_1.createNewFile();
            out = new FileOutputStream(file_1);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
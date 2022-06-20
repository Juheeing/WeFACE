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

public class MouthCameraActivity2 extends AppCompatActivity {

    Button btn_cam, btn_gallery, btn_close;
    String path;
    File file_2, file2;
    Bitmap bitmap2, rotatedBitmap2;
    public static Context context_mouth_camera2;
    private static final int REQUEST_CAMERA_CODE = 100;
    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouth_camera2);

        if (ContextCompat.checkSelfPermission(MouthCameraActivity2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MouthCameraActivity2.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA_CODE);
        }

        context_mouth_camera2 = this;
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
                    String imageFileName = "target";

                    try {
                        File tempImage = File.createTempFile(imageFileName, ".jpg", tempDir);
                        path = tempImage.getAbsolutePath();
                        photoFile = tempImage;

                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(MouthCameraActivity2.this, getPackageName() + ".fileprovider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, 2);
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
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                file2 = new File(path);
                try{

                    bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file2));

                    if (bitmap2 != null) {
                        ExifInterface ei = new ExifInterface(path);
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        rotatedBitmap2 = null;
                        switch (orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap2 = rotateImage2(bitmap2, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap2 = rotateImage2(bitmap2, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap2 = rotateImage2(bitmap2, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap2 = bitmap2;

                        }
                        SaveBitmapToFileCache(rotatedBitmap2, getFilesDir() + "/" + "target.jpg");

                        Intent intent = new Intent(MouthCameraActivity2.this, MouthImageActivity3.class);
                        startActivity(intent); }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        }else if(requestCode== REQUEST_CODE && resultCode==RESULT_OK && data!=null) {
            Uri photoUri = data.getData();

            try {
                rotatedBitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(),photoUri);
                SaveBitmapToFileCache(rotatedBitmap2, getFilesDir() + "/" + "target.jpg");
                Intent intent = new Intent(MouthCameraActivity2.this, MouthImageActivity3.class);
                startActivity(intent);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static Bitmap rotateImage2(Bitmap source, float angle){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void SaveBitmapToFileCache(Bitmap bitmap, String strFilePath) {

        file_2 = new File(strFilePath);
        OutputStream out = null;
        try {
            file_2.createNewFile();
            out = new FileOutputStream(file_2);
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
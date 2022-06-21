package com.example.Eye_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MouthResultActivity extends AppCompatActivity {

    ImageView iv_result;
    Button btn_save, btn_back, image_next, btn_before;
    Bitmap bitmap;
    File file;
    String Imageurl = "http://52.79.174.94:8000/change/image/result/result.jpg";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouth_result);

        iv_result = (ImageView) findViewById(R.id.iv_result);
        btn_before = (Button) findViewById(R.id.btn_before);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_back = (Button) findViewById(R.id.btn_back);
        image_next = (Button) findViewById(R.id.image_next);

        Thread Thread = new Thread() {
            @Override
            public void run(){
                try{

                    URL url = new URL(Imageurl);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        };

        Thread.start();

        try{

            Thread.join();
            iv_result.setImageBitmap(bitmap);
            SaveBitmapToFileCache(bitmap, getFilesDir() + "/" + "juhee.jpg");
            UploadUtils.send_image(file, user.getInstance().getUsername());

        }catch (InterruptedException e){

            e.printStackTrace();

        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iv_result == null){
                    return;
                }
                saveImage(((BitmapDrawable)iv_result.getDrawable()).getBitmap(), sdf.format(new Date()));
                Toast.makeText(MouthResultActivity.this, "갤러리에 저장되었습니다", Toast.LENGTH_SHORT).show();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MouthResultActivity.this, MouthResultActivity.class);
                startActivity(intent);
            }
        });

        btn_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MouthResultActivity.this, BeforeMouthActivity.class);
                startActivity(intent);
            }
        });


        image_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MouthResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean saveImage(Bitmap bitmap, String saveImageName) {
        String saveDir = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DCIM).toString()+ "/Project";
        File file = new File(saveDir);
        if (!file.exists()) {
            file.mkdir();
        }

        String fileName = saveImageName + ".jpg";
        File tempFile = new File(saveDir, fileName);
        FileOutputStream output = null;

        try {
            if (tempFile.createNewFile()) {
                output = new FileOutputStream(tempFile);
                Bitmap newBitmap = bitmap.createScaledBitmap(bitmap, 2420, 3226, true);
                newBitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(tempFile));
                sendBroadcast(intent);

            } else {
                Log.d("TEST_LOG", "같은 이름의 파일 존재:"+saveImageName);

                return false;
            }
        } catch (FileNotFoundException e) {
            Log.d("TEST_LOG", "파일을 찾을 수 없음");
            return false;

        } catch (IOException e) {
            Log.d("TEST_LOG", "IO 에러");
            e.printStackTrace();
            return false;

        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private void SaveBitmapToFileCache(Bitmap bitmap, String strFilePath) {

        file = new File(strFilePath);
        OutputStream out = null;
        try {
            file.createNewFile();
            out = new FileOutputStream(file);
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
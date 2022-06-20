package com.example.Eye_test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ItemAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Item> items;
    Item item;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");

    public ItemAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.recycler_item, parent, false);

        VH holder = new VH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        item = items.get(position);
        Glide.with(context).load(item.getImgPath()).into(vh.iv);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public boolean saveImage(Bitmap bitmap, String saveImageName) {
        String saveDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()+ "/Project";
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
                context.sendBroadcast(intent);

            } else {
                // 같은 이름의 파일 존재
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

    class VH extends RecyclerView.ViewHolder{
        ImageView iv;
        Button btn_save;

        public VH(@NonNull View itemView){
            super(itemView);

            iv = itemView.findViewById(R.id.iv);
            btn_save = itemView.findViewById(R.id.btn_save);

            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveImage(((BitmapDrawable)iv.getDrawable()).getBitmap(), sdf.format(new Date()));
                    Toast.makeText(context, "갤러리에 저장되었습니다", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}

package com.example.Eye_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MypageActivity extends AppCompatActivity {

    TextView textview;
    ArrayList<Item> items = new ArrayList<>();
    RecyclerView recyclerView;
    ItemAdapter adapter;
    String url = "http://52.79.174.94:8000/change/mypage/?username=" + user.getInstance().getUsername();
    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        textview = findViewById(R.id.textview);

        if(user.getInstance().getUsername() == ""){
            Toast.makeText(MypageActivity.this, "로그인 후 이용하세요", Toast.LENGTH_LONG).show();
        }else {
            textview.setText(user.getInstance().getUsername() + "님의 Gallery");

            recyclerView = findViewById(R.id.recycler);
            adapter = new ItemAdapter(this, items);
            recyclerView.setAdapter(adapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    //파라미터로 응답받은 결과 JsonArray를 분석

                    items.clear();
                    adapter.notifyDataSetChanged();
                    try {

                        for(int i=0;i<response.length();i++){
                            JSONObject jsonObject= response.getJSONObject(i);

                            imgPath=jsonObject.getString("image");

                            items.add(0,new Item("http://52.79.174.94:8000"+imgPath));
                            adapter.notifyItemInserted(0);
                        }
                    } catch (JSONException e) {e.printStackTrace();}

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MypageActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            });

            //실제 요청 작업을 수행해주는 요청큐 객체 생성
            RequestQueue requestQueue= Volley.newRequestQueue(this);

            //요청큐에 요청 객체 생성
            requestQueue.add(jsonArrayRequest);
        }
    }
}
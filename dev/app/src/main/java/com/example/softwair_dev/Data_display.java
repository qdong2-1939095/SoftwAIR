package com.example.softwair_dev;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Data_display extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";

    ListView l;
    private static ArrayList<String>  data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);


        l = findViewById(R.id.list);
        data = new ArrayList<>();
        data.add("Please Refresh");
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String> (this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                data);
        l.setAdapter(arr);



        findViewById(R.id.button_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<LatestRecord> call = retrofitInterface.executeQueryLatest();
                call.enqueue(new Callback<LatestRecord>() {
                    @Override
                    public void onResponse(Call<LatestRecord> call, Response<LatestRecord> response) {
                        if (response.code() == 200) {
                            arr.notifyDataSetChanged();
                            Toast.makeText(Data_display.this, "Data retrived",
                                    Toast.LENGTH_LONG).show();
                            String s = response.body().getRecord();
                            System.out.println(s);
                            HashMap<String,String> result = null;
                            try {
                                result = jsonToMap(s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ArrayList<String> newData = new ArrayList<>();
                            Iterator arrIterator = result.keySet().iterator();
                            while (arrIterator.hasNext()) {
                                String mapElement = (String) arrIterator.next();
                                String element = mapElement + " : "+ result.get(mapElement);
                                newData.add(element);
                                Toast.makeText(Data_display.this, element,
                                        Toast.LENGTH_LONG).show();
                            }
                            data = newData;
                        } else if (response.code() == 400) {
                            Toast.makeText(Data_display.this, "AWS query failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    public HashMap<String,String> jsonToMap(String t) throws JSONException {

                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject jObject = new JSONObject(t);
                        Iterator<?> keys = jObject.keys();

                        while( keys.hasNext() ){
                            String key = (String)keys.next();
                            String value = jObject.getString(key);
                            map.put(key, value);

                        }
                        return map;
                    }
                    @Override
                    public void onFailure(Call<LatestRecord> call, Throwable t) {
                        Toast.makeText(Data_display.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }
}

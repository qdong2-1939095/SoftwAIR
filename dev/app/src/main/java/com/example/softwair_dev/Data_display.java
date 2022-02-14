package com.example.softwair_dev;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

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
                            Toast.makeText(Data_display.this, "Data Updated!",
                                    Toast.LENGTH_LONG).show();
                            LatestRecord r =  response.body();
                            ArrayList<String> newData= r.getResult();
                            data.clear();
                            for (int i = 0; i < newData.size(); i++){
                                data.add(newData.get(i));
                            }
                            arr.notifyDataSetChanged();
                        } else if (response.code() == 400) {
                            Toast.makeText(Data_display.this, "AWS query failed",
                                    Toast.LENGTH_LONG).show();
                        }
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

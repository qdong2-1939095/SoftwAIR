package com.example.softwair_dev;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

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
    String paramteres[] = { "ID", "Name",
            "Temp", "E_PM",
            "S_PM", "RH" };
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
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                paramteres);
        l.setAdapter(arr);


        findViewById(R.id.button_refresh).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                handRefreshDialog();
            }
        });
    }
    private void handRefreshDialog(){

        View view = getLayoutInflater().inflate(R.layout.activity_data_display, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view).show();

        Button refresh_button = view.findViewById(R.id.button_refresh);

        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<LatestRecord> call = retrofitInterface.executeQueryLatest();

                call.enqueue(new Callback<LatestRecord>() {
                    @Override
                    public void onResponse(Call<LatestRecord> call, Response<LatestRecord> response) {

                        if (response.code() == 200) {
                            update();
                        } else if (response.code() == 400) {
                            Toast.makeText(Data_display.this, "AWS query failed",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                    private void update(){
                        return;
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

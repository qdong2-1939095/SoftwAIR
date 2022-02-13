package com.example.softwair_dev;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;

public class Data_display extends AppCompatActivity {

    ListView l;
    String paramteres[]
            = { "ID", "Name",
            "Temp", "E_PM",
            "S_PM", "RH"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);

        l = findViewById(R.id.list);
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>( this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paramteres);
        l.setAdapter(arr);
    }



}
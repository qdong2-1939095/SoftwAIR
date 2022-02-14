package com.example.softwair_dev;

import java.util.ArrayList;

// contains one latest row of record of certain device(hardcode decive ID for now)
public class LatestRecord {
    private String Env_PM_smaller_than_10;
    private String PC_0_3um;
    private String PC_0_5um;
    private String PC_5um;
    private String time;
    private String Env_PM_smaller_than_2_5;
    private String Relative_Humidity;
    private String date;
    private String pm100_std;
    private String PC_1_0num;
    private String Battery;
    private String Temperature_c;
    private String PC_10um;
    private String PC_2_5um;
    private String pm25_std;
    private String id;
    private String pm10_std;

    public ArrayList<String> getResult(){
        ArrayList<String> result = new ArrayList<>();
        result.add("Env_PM_smaller_than_10: " + Env_PM_smaller_than_10);
        result.add("PC_0_3um: " + PC_0_3um);
        result.add("PC_0_5um: "+ PC_0_5um);
        result.add( "PC_5um: " + PC_5um);
        result.add( "time: " + time);
        result.add("Env_PM_smaller_than_2_5: " + Env_PM_smaller_than_2_5);
        result.add( "Relative_Humidity :"+Relative_Humidity);
        result.add( "date: " + date);
        result.add( "pm100_std: "+pm100_std);
        result.add( "PC_1_0num: "+PC_1_0num);
        result.add( "Battery: "+Battery);
        result.add( "Temperature_c: "+Temperature_c);
        result.add( "PC_10um :"+PC_10um);
        result.add( "PC_2_5um: "+PC_2_5um);
        result.add( "pm25_std: "+pm25_std);
        result.add( "id: "+id);
        result.add( "pm10_std: "+pm10_std);
        return result;
    }
}

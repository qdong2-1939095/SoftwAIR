package com.example.softwair_dev;

import java.util.HashMap;

// contains one latest row of record of certain device(hardcode decive ID for now)
public class LatestRecord {
    private HashMap<String, String> record;
    public HashMap<String, String> getRecord() {
        return record;
    }
}

package com.consolegame.player;

import com.consolegame.helper.type.FieldType;

public class Clock {
    private int time = 0;
    
    public void setTime(int time) { this.time = time; }
    public int getTime() { return time; }
    public void increaseTime(FieldType fieldType) {
        if (time < 12 && (fieldType == FieldType.CLOCK1 || fieldType == FieldType.CLOCK2))
            time++;
        else System.out.println("Time unchanged");
    }
}
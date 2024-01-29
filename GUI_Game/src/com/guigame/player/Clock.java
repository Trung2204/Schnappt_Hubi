package com.guigame.player;

import com.guigame.helper.type.FieldType;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class Clock {
    private int time = 0;
    
    public void setTime(int time) { this.time = time; }
    public int getTime() { return time; }
    public void increaseTime(FieldType fieldType) {
        if (time < 12 && (fieldType == FieldType.CLOCK1 || fieldType == FieldType.CLOCK2))
            time++;
        else System.out.println("Time unchanged");
    }
    public VBox drawClock() {
    	Label clockLabel = new Label("CLOCK");
    	TextArea timeText = new TextArea("Time: "+this.time);
        
        VBox clockLayout = new VBox(clockLabel,timeText);
    	return clockLayout;
    }
}
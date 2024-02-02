package com.guigame.board;
import java.util.Random;

import com.guigame.helper.type.FieldType;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class Compass {
    private FieldType fieldType;
    
    public Compass() {
    	this.fieldType = FieldType.NONE;
    }
    public FieldType getFieldType() { return fieldType; }
    public int getNumberOfAction() { return fieldType.getAction(); }
    public void spin() {
    	Random random = new Random();
        int angle = random.nextInt(360);

        if (angle <= 72) { // CLOCK1
        	this.fieldType = FieldType.CLOCK1;
        } else if (73 <= angle && angle <= 126) { // TWO
        	this.fieldType = FieldType.TWO;
        } else if (127 <= angle && angle <= 180) { // THREE
        	this.fieldType = FieldType.THREE;
        } else if (181 <= angle && angle <= 252) { // CLOCK2
        	this.fieldType = FieldType.CLOCK2;
        } else { // GHOST
        	this.fieldType = FieldType.GHOST;
        }
    }
    public VBox drawCompass() {
    	Label compassLabel = new Label("COMPASS");
    	TextArea fieldText = new TextArea("Field: "+this.fieldType);
    	TextArea numberOfActionsText = new TextArea("Number of actions: "+this.getNumberOfAction());
        
        VBox compassLayout = new VBox(compassLabel,fieldText,numberOfActionsText);
    	return compassLayout;
    }
}
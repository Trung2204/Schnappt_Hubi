package com.guigame.application.model.helper.type;
public enum FieldType {
    CLOCK1(1),
    CLOCK2(1),
    GHOST(1),
    TWO(2),
    THREE(3),
	
    NONE(0);

    private final int action;

    FieldType(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }
}

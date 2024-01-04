package main;
public class Clock {

    private static int time = 0;
    
    public int getTime() { return time; }
    public void increaseTime(FieldType fieldType) {
        if (time < 12 && (fieldType == FieldType.CLOCK1 || fieldType == FieldType.CLOCK2))
            time++;
        else System.out.println("Time unchanged");
    }
}
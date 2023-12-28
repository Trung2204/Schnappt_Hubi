package Main;
public class Clock {

    private static int time;
    private static Compass compass;
    
    public Clock() {
        Clock.time = 0;
    }

    public void setCompass(Compass compass) {
        Clock.compass = compass;
    }

    public void increaseTime(FieldType fieldType) {
        if (time < 12 && (fieldType == FieldType.CLOCK1 || fieldType == FieldType.CLOCK2))
            time++;
        else System.out.println("can't increase time");
        return;
    }

    public int getTime() {
        return time;
    }

    public Compass getCompass() {
        return compass;
    }
}
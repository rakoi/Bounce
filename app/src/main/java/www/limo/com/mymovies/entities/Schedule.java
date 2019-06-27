package www.limo.com.mymovies.entities;

import java.util.*;

public class Schedule {
    private String time;
    private String[] days;

    public String getTime() { return time; }
    public void setTime(String value) { this.time = value; }

    public String[] getDays() { return days; }
    public void setDays(String[] value) { this.days = value; }
}

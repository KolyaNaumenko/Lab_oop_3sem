package model;

import java.sql.Date;
import java.sql.Time;

public class Race {
    private int raceId;
    private Date date;
    private Time time;
    private String location;
    private String status;

    public Race() {
    }

    public Race(int raceId, Date date, Time time, String location, String status) {
        this.raceId = raceId;
        this.date = date;
        this.time = time;
        this.location = location;
        this.status = status;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

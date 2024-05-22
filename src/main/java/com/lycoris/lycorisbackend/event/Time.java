package com.lycoris.lycorisbackend.event;

public class Time {

    private int heures;
    private int minutes;// de 0 à 59
    private int secondes; // de 0 à 59
    private int days;
    private int month;


    public Time(int h, int m) {// initialise heures, minutes,
        heures = h; // secondes avec h, m, s
        minutes = m;
    }

    public Time(int month, int days,int h, int m) {// initialise heures, minutes,
        this.month = month;
        this.days = days;
        heures = h; // secondes avec h, m, s
        minutes = m;
    }


    public int getHeures() {
        return heures;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSecondes() {
        return secondes;
    }

    public void setHeures(int h) {
        heures = h;
    }

    public int timeSince(Time t) {
        int duration = 0;
        if (days == 0){
            duration = heures * 60 + minutes - t.getMinutes() - t.getHeures() * 60;
        }

        return duration;
    }
}



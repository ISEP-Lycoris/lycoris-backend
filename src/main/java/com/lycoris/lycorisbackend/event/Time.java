package com.lycoris.lycorisbackend.event;

public class Time {

    private int heures;
    private int minutes;// de 0 à 59
    private int secondes; // de 0 à 59


    public Time(int h, int m, int s) {// initialise heures, minutes,
        heures = h; // secondes avec h, m, s
        minutes = m;
        secondes = s;
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
}



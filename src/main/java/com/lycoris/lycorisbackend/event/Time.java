package com.lycoris.lycorisbackend.event;

public class Time {

    private int heures;
    private int minutes;// de 0 à 59
    private int days;
    private int month;
    private int year;


    public Time(int year, int month, int days,int hour, int minutes) {// initialise heures, minutes,
        this.month = month;
        this.days = days;
        this.heures = hour;
        this.minutes = minutes;
        this.year = year;
    }
    public Time(Time time) {
        this.heures = time.heures;
        this.minutes = time.minutes;
        this.days = time.days;
        this.month = time.month;
        this.year = time.year;
    }

    public void addTime(int minutes){
        this.minutes += minutes;
        while(this.minutes >= 60){
            this.minutes -= 60;
            heures +=1;
        }
        while(this.heures >= 24){
            this.heures -= 24;
            days +=1;
        }
        while(this.days > lenghtMonth()){
            this.days -= lenghtMonth()-1;
            month +=1;
        }
        while(this.month > 12){
            this.month -= 11;
            year +=1;
        }
    }


    public int timeSince(Time t) {
        int totalMinutes = 0;
        int yearDifference = this.year - t.year;
        for (int i = 0; i < Math.abs(yearDifference); i++) {
            if (yearDifference > 0) {
                totalMinutes += (t.lengthYear() * 24 * 60);
                t.year++;
            } else {
                totalMinutes -= (this.lengthYear() * 24 * 60);
                this.year--;
            }
        }
        int monthDifference = this.month - t.month;
        for (int i = 0; i < Math.abs(monthDifference); i++) {
            if (monthDifference > 0) {
                totalMinutes += (t.lenghtMonth() * 24 * 60);
                t.month++;
            } else {
                totalMinutes -= (this.lenghtMonth() * 24 * 60);
                this.month--;
            }
        }
        int dayDifference = this.days - t.days;
        totalMinutes += dayDifference * 24 * 60;
        int hourDifference = this.heures - t.heures;
        totalMinutes += hourDifference * 60;
        int minuteDifference = this.minutes - t.minutes;
        totalMinutes += minuteDifference;
        return totalMinutes;
    }

    public boolean isBefore(Time than) {
        return timeSince(than)<=0;
    }
    public boolean isAfter(Time than) {
        return timeSince(than)>=0;
    }

    public int lenghtMonth(){
        int result = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                result =  31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                result = 30;
                break;
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    result = 29;
                    break;
                } else {
                    result = 28;
                    break;
                }
            default:
                result =  0;
                break;
        }
        return result;
    }
    public int lengthYear() {
        int result = 0;
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            result = 366;
        } else {
            result = 365;
        }
        return result;
    }



}



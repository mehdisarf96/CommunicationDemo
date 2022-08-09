package com.mehdisarf.communicationdemo.entities;

import java.io.Serializable;

public class Uptime implements Serializable {

    private Integer day;
    private Long hours;
    private Long minutes;
    private Long seconds;

    public Uptime(Integer day, Long hours, Long minutes, Long seconds) {
        this.day = day;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    public Long getSeconds() {
        return seconds;
    }

    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }
}

package com.mehdisarf.communicationdemo.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HealthReport implements Serializable {

    private final static String NAME = "hotel (java)";
    private String ip;
    private LocalDateTime startTime;
    private Uptime uptime;
    private final static String version = "SM-Hotel-0.0.425";
    private String dbVersion;

    public static String getNAME() {
        return NAME;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Uptime getUptime() {
        return uptime;
    }

    public void setUptime(Uptime uptime) {
        this.uptime = uptime;
    }

    public String getVersion() {
        return version;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }
}

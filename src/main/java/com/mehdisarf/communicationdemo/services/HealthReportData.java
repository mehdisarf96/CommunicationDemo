package com.mehdisarf.communicationdemo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehdisarf.communicationdemo.entities.HealthReport;
import com.mehdisarf.communicationdemo.entities.Uptime;
import com.mehdisarf.communicationdemo.util.PersistenceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class HealthReportData {

    private static MetricsEndpoint metricsEndpoint;
    private final static EntityManagerFactory emf = PersistenceUtil.getInstance().getEntityManagerFactory();
    private static ObjectMapper objectMapper;

    public HealthReportData() {
    }

    @Autowired
    public HealthReportData(MetricsEndpoint metricsEndpoint, ObjectMapper objectMapper) {
        this.metricsEndpoint = metricsEndpoint;
        this.objectMapper = objectMapper;
        objectMapper.findAndRegisterModules(); // Customize the Jackson ObjectMapper.
        // exception ii ke tuye method ii az controlleram va moqeye
        // return kardan e object tuye method e controller ke marbut be LocalDateTime
        // bud, throw mishod, bartaraf shod.
        // btw, man asan az in objectMapper ke field e in classe, hichja estefade nakardm.
    }

    public HealthReport generateHealthReport() {

        HealthReport report = new HealthReport();
        report.setIp(getIP());
        report.setUptime(getUptime());
        report.setStartTime(getStartTime());
        report.setDbVersion(getDatabaseVersion());

        return report;
    }

    private static String getDatabaseVersion() {

        EntityManager entityManager = emf.createEntityManager();
        Query query = entityManager.createNativeQuery("SELECT VERSION()");
        String result = (String) query.getResultList().get(0);
        entityManager.close();

        return result.substring(0, result.lastIndexOf("-"));
    }

    private static String getIP() {

        InetAddress inetAddress = null;

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return inetAddress.getHostAddress();
    }

    private static Uptime getUptime() {

        long uptimeSeconds = metricsEndpoint.metric("process.uptime", null)
                .getMeasurements()
                .get(0)
                .getValue().longValue();

        int day = (int) TimeUnit.SECONDS.toDays(uptimeSeconds);
        long hours = TimeUnit.SECONDS.toHours(uptimeSeconds) -
                TimeUnit.DAYS.toHours(day);
        long minute = TimeUnit.SECONDS.toMinutes(uptimeSeconds) -
                TimeUnit.DAYS.toMinutes(day) -
                TimeUnit.HOURS.toMinutes(hours);
        long second = TimeUnit.SECONDS.toSeconds(uptimeSeconds) -
                TimeUnit.DAYS.toSeconds(day) -
                TimeUnit.HOURS.toSeconds(hours) -
                TimeUnit.MINUTES.toSeconds(minute);

        return new Uptime(day, hours, minute, second);
    }

    private static LocalDateTime getStartTime() {
        long startTime = metricsEndpoint.metric("process.start.time", null)
                .getMeasurements()
                .get(0).getValue().longValue();

        ZonedDateTime zoneDateTime = Instant.ofEpochSecond(startTime).atZone(ZoneId.of("Iran"));
        return zoneDateTime.toLocalDateTime();
    }
}








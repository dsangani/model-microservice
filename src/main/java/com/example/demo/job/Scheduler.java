package com.example.demo.job;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.demo.dao.DbStore;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    @Scheduled(cron = "*/5 * * * * *")
    public void cronJobSch() throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        ResultSet rs = DbStore.getInstance().connect().prepareStatement("select count(*) from person").executeQuery();
        rs.next();
        int count = rs.getInt(1);
        System.out.println("Java cron job expression:: " + strDate);
        System.out.println("Total entries in db:: " + count);
    }
}

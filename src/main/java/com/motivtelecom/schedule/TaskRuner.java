package com.motivtelecom.schedule;

import com.motivtelecom.db.AsrAutoTask;
import com.motivtelecom.db.JDBCQuerries;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
public class TaskRuner implements Runnable{

    @Getter@Setter private String userName;

    @Override
    public void run() {
    }
    private void addToNextLaunchTime(AsrAutoTask task) {
        String[] parse_interval = task.getStartInterval().split(";");
        Date date_to_modify = task.getNextStartTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date_to_modify);
        for (String sub_parse:parse_interval){
            String[] s = sub_parse.split(":");
            if (s[0].matches("MIN")) calendar.add(Calendar.MINUTE, Integer.parseInt(s[1]));
            if (s[0].matches("MONTH")) calendar.add(Calendar.MONTH, Integer.parseInt(s[1]));
            if (s[0].matches("HOUR")) calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(s[1]));
            if (s[0].matches("DAY")) calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(s[1]));
        }
        JDBCQuerries.whriteLogtab(
                "START"+";"
                        +"USER::"+userName+";"
                        +"TASK_ID::"+task.getId()+";"
        );
        task.setNextStartTime(calendar.getTime());
        JDBCQuerries.updateTaskNextStartTime(task);
    }
}
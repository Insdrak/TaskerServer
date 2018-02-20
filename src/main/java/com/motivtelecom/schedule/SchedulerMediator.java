package com.motivtelecom.schedule;

import com.motivtelecom.db.AsrAutoTask;
import com.motivtelecom.db.JDBCQuerries;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;

public class SchedulerMediator {

    @Scheduled(fixedDelay = 500)
    public void mainScanSchedule () {
        System.out.printf("trst");
        /*ArrayList<AsrAutoTask> allTasks = JDBCQuerries.getTasksHeaderData ();
        for (AsrAutoTask task : allTasks) {
            DateTime dateTime = new DateTime();
            //dateTime = dateTime.minusHours(1);// local PC FIX couse of bad time zone
            DateTime dateTime1 = new DateTime(task.getNextStartTime().getTime());
            Long l = dateTime.getMillis() - dateTime1.getMillis();
            Long l2 = Long.valueOf(500) / 2;
            l = Math.abs(l);
            Boolean isNeedToRun = (l < l2);
            if (isNeedToRun) {
                // ADD TIME\
                //add_to_next_launch_time(task);
                // PROCESS THIS TASK ----
                //process_task(task);
            }
        }*/
    }
}

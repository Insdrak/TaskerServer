package com.motivtelecom.schedule;

import com.motivtelecom.db.AsrAutoTask;
import com.motivtelecom.db.JDBCQuerries;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class SchedulerMediator {

    private static Integer i = 12345000;
    private static String queueName = "";
    private static String msgPart1 = "";
    private static String msgPart2 = "";

    @Scheduled(fixedDelay = 500)
    public void mainScanSchedule () {
        System.out.println(i++);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = null;
        try {
            connection = factory.newConnection();
            Channel channel = connection.createChannel();
            try {
                channel.queueDeclare(queueName, false, false, false, null);
                String message = msgPart1 + i + msgPart2;
                channel.basicPublish("", queueName, null, message.getBytes());
            }
            finally {
                channel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

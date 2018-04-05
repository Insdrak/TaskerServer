package com.motivtelecom.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aoi on 26.07.2017.
 *  taskStatus - 1:ready to run,0:runing,-1:error,
 */

@ToString(exclude = {"id","nextStartTime","startInterval","subTasks"})
public class AsrAutoTask {
    @Getter@Setter private BigDecimal id;
    @Getter@Setter private String nameDescription;
    @Getter@Setter private Date nextStartTime;
    @Getter@Setter private String startInterval;
    @Getter@Setter private String mailSendTo;
    @Getter@Setter private String mailSendC;
    @Getter@Setter private String mailSendCC;
    @Getter@Setter private Integer taskStatus;
    @Getter@Setter private ArrayList<AsrAutoSubTask> subTasks;

}

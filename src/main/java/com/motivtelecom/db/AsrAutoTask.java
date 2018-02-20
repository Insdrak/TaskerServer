package com.motivtelecom.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aoi on 26.07.2017.
 */
public class AsrAutoTask {
    private BigDecimal id;
    private String nameDescription;
    private Date nextStartTime;
    private String startInterval;
    private ArrayList<AsrAutoSubTask> subTasks;

    @Override
    public String toString() {
        return this.getNameDescription();
    }

    public ArrayList<AsrAutoSubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<AsrAutoSubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNameDescription() {
        return nameDescription;
    }

    public Date getNextStartTime() {
        return nextStartTime;
    }

    public void setNextStartTime(Date nextStartTime) {
        this.nextStartTime = nextStartTime;
    }

    public String getStartInterval() {
        return startInterval;
    }

    public void setStartInterval(String startInterval) {
        this.startInterval = startInterval;
    }

    public void setNameDescription(String nameDescription) {
        this.nameDescription = nameDescription;
    }
}

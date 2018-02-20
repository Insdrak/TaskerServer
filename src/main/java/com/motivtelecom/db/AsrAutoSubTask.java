package com.motivtelecom.db;

import java.math.BigDecimal;

/**
 * Created by Aoi on 26.07.2017.
 */
public class AsrAutoSubTask {
    private BigDecimal parentTaskId;
    private BigDecimal id;
    private String sqlText;
    private String runUserString;

    public BigDecimal getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(BigDecimal parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public String getRunUserString() {
        return runUserString;
    }

    public void setRunUserString(String runUserString) {
        this.runUserString = runUserString;
    }
}

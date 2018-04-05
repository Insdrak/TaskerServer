package com.motivtelecom.db;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Aoi on 26.07.2017.
 */
class AsrAutoSubTask {
    @Getter@Setter private BigDecimal parentTaskId;
    @Getter@Setter private BigDecimal id;
    @Getter@Setter private String sqlText;
    @Getter@Setter private String runUserString;
}

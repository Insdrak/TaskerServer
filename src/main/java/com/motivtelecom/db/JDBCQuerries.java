package com.motivtelecom.db;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Aoi on 27.01.2017.
 */
@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
public class JDBCQuerries {
    private static JdbcTemplate template;

    private final static Logger logger = Logger.getLogger(JDBCQuerries.class);

    public JDBCQuerries(ApplicationContext context) {
        template = (JdbcTemplate) context.getBean("main");
    }

    public static ArrayList<AsrAutoTask> getTasksHeaderData() {
        String sql =
                " SELECT * "+
                " FROM STATBOY.ASR_AUTO_TASKS AAT "+
                " ORDER BY AAT.ASR_AUTO_TASK_ID ";

        List<Map<String, Object>> result = template.queryForList(sql, new Object[]{});

        ArrayList<AsrAutoTask> out = new ArrayList<>();

        for (Map<String,Object> ires:result){
            AsrAutoTask task = new AsrAutoTask();
            task.setId((BigDecimal) ires.get("ASR_AUTO_TASK_ID"));
            task.setNameDescription((String) ires.get("ASR_AUTO_TASK_DESC"));
            task.setNextStartTime((Timestamp) ires.get("ASR_AUTO_TASK_START_TIME"));
            task.setStartInterval((String) ires.get("ASR_AUTO_TASK_START_INTERVAL"));
            out.add(task);
        }
        return out;
    }

    public ArrayList<AsrAutoSubTask> getSubTaskByTaskData(AsrAutoTask asrAutoTask) {
        String sql =
                " SELECT * "+
                " FROM STATBOY.ASR_AUTO_SUB_TASKS AAST "+
                " WHERE AAST.ASR_AUTO_TASK_ID = "+asrAutoTask.getId()+" "+
                " ORDER BY 1 ";

        List<Map<String, Object>> result = template.queryForList(sql, new Object[]{});

        ArrayList<AsrAutoSubTask> out = new ArrayList<>();

        for (Map<String,Object> ires:result){
            AsrAutoSubTask asrAutoSubTask = new AsrAutoSubTask();
            asrAutoSubTask.setId((BigDecimal) ires.get("ASR_AUTO_SUB_TASK_ID"));
            asrAutoSubTask.setParentTaskId((BigDecimal) ires.get("ASR_AUTO_TASK_ID"));
            asrAutoSubTask.setRunUserString((String) ires.get("ASR_AUTO_SUB_TASK_RUN_USER"));
            asrAutoSubTask.setSqlText((String) ires.get("ASR_AUTO_SUB_TASK_SQL"));
            out.add(asrAutoSubTask);
        }

        return out;
    }

    public void updateTaskNextStartTime(AsrAutoTask asrAutoTask) {
        String sql =
                " UPDATE "+
                " STATBOY.ASR_AUTO_TASKS "+
                " SET " +
                " ASR_AUTO_TASK_START_TIME = TO_DATE('"+OracleDateFormatter.formatWithDf(asrAutoTask.getNextStartTime())+"','dd.mm.yyyy hh24:mi:ss') "+
                " WHERE " +
                " ASR_AUTO_TASK_ID = "+ asrAutoTask.getId() +" ";
        template.execute(sql);
    }

    public void update_task_start_interval(AsrAutoTask asr_auto_task) {
        String sql =
                " UPDATE "+
                        " STATBOY.ASR_AUTO_TASKS "+
                        " SET " +
                        " ASR_AUTO_TASK_START_INTERVAL = '"+asr_auto_task.getStartInterval()+"' "+
                        " WHERE " +
                        " ASR_AUTO_TASK_ID = "+ asr_auto_task.getId() +" ";
        template.execute(sql);
    }

    public void update_task_name_description(AsrAutoTask asr_auto_task) {
        String sql =
                " UPDATE "+
                        " STATBOY.ASR_AUTO_TASKS "+
                        " SET " +
                        " ASR_AUTO_TASK_DESC = '"+asr_auto_task.getNameDescription()+"' "+
                        " WHERE " +
                        " ASR_AUTO_TASK_ID = "+ asr_auto_task.getId() +" ";
        template.execute(sql);
    }

    public void whrite_logtab (String message){
        String sql =
                " BEGIN "+
                        " sysbee.sk_logtab_pkg.writelogtab(incid => 'ASR_AUTO', insubcid =>'EXECUTE_TASK_SERVER' , indat => sysdate, indescr => q'<"+message+">' ); "+
                        " END; ";
        template.execute(sql);
    }

    public void update_sub_task_sql(final AsrAutoSubTask asr_auto_sub_task) {
        //asr_auto_sub_task.setSqlText(asr_auto_sub_task.getSqlText().replace("'","''"));
        //Clob clob =
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement
                        (
                                " UPDATE " +
                                        " STATBOY.ASR_AUTO_SUB_TASKS " +
                                        " SET " +
                                        " ASR_AUTO_SUB_TASK_SQL = ? " +
                                        " WHERE " +
                                        " ASR_AUTO_TASK_ID = ? " +
                                        " AND " +
                                        " ASR_AUTO_SUB_TASK_ID = ? "
                        );
                Clob clob = connection.createClob();
                clob.setString(1,asr_auto_sub_task.getSqlText());
                statement.setClob(1,clob);
                statement.setBigDecimal(2,asr_auto_sub_task.getParentTaskId());
                statement.setBigDecimal(3,asr_auto_sub_task.getId());
                return  statement;
            }
        });

        /*String sql =
                " UPDATE "+
                        " STATBOY.ASR_AUTO_SUB_TASKS "+
                        " SET " +
                        " ASR_AUTO_SUB_TASK_SQL = to_clob(q'<"+asr_auto_sub_task.getSqlText()+">') "+
                        " WHERE " +
                        " ASR_AUTO_TASK_ID = "+ asr_auto_sub_task.getParentTaskId() +" "+
                        " AND " +
                        " ASR_AUTO_SUB_TASK_ID = "+ asr_auto_sub_task.getId() +" ";
        template.execute(sql);*/
    }

    public void update_sub_task_run_user(AsrAutoSubTask asr_auto_sub_task) {
        String sql =
                " UPDATE "+
                        " STATBOY.ASR_AUTO_SUB_TASKS "+
                        " SET " +
                        " ASR_AUTO_SUB_TASK_RUN_USER = '"+asr_auto_sub_task.getRunUserString()+"' "+
                        " WHERE " +
                        " ASR_AUTO_TASK_ID = "+ asr_auto_sub_task.getParentTaskId() +" "+
                        " AND " +
                        " ASR_AUTO_SUB_TASK_ID = "+ asr_auto_sub_task.getId() +" ";
        template.execute(sql);
    }

    private BigDecimal get_next_asr_auto_tasks_id(){
        String sql =
                "SELECT MIN(AAT1.ASR_AUTO_TASK_ID)+1 "+
                "FROM STATBOY.ASR_AUTO_TASKS AAT1 "+
                "WHERE NOT EXISTS(SELECT * FROM STATBOY.ASR_AUTO_TASKS AAT2 WHERE AAT2.ASR_AUTO_TASK_ID = AAT1.ASR_AUTO_TASK_ID + 1) "+
                "ORDER BY AAT1.ASR_AUTO_TASK_ID ";
        return template.queryForObject(sql,BigDecimal.class);
    }

    private BigDecimal get_next_asr_auto_sub_tasks_id(BigDecimal task_id) {
        String sql =
            " SELECT NVL(MIN (AAST1.ASR_AUTO_SUB_TASK_ID) + 1,1) "+
            " FROM STATBOY.ASR_AUTO_SUB_TASKS AAST1 "+
            " WHERE NOT EXISTS (SELECT * FROM STATBOY.ASR_AUTO_SUB_TASKS AAST2 WHERE AAST2.ASR_AUTO_SUB_TASK_ID = AAST1.ASR_AUTO_SUB_TASK_ID + 1) "+
            " AND AAST1.ASR_AUTO_TASK_ID = "+task_id+
            " ORDER BY AAST1.ASR_AUTO_SUB_TASK_ID ";
        return template.queryForObject(sql,BigDecimal.class);
    }

    public String call_task(String sql_text, JdbcTemplate jdbcTemplate) {
        try {
            try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql_text)){
                    preparedStatement.execute();
                } catch (SQLException e1) {
                    logger.error(e1);
                    e1.printStackTrace();
                }
            } catch (SQLException e) {
                logger.error(e);
                e.printStackTrace();
            }
            return "OK";
        }
        catch (Exception e){
            return e.getLocalizedMessage();
        }
    }

    public String add_new_task() {
        try {
            String sql =
                    " INSERT "+
                            " INTO "+
                            " STATBOY.ASR_AUTO_TASKS AAT "+
                            " (AAT.ASR_AUTO_TASK_ID,AAT.ASR_AUTO_TASK_DESC,AAT.ASR_AUTO_TASK_START_TIME,AAT.ASR_AUTO_TASK_START_INTERVAL) "+
                            " VALUES "+
                            " ( "+
                            " "+get_next_asr_auto_tasks_id()+", "+
                            " 'Новая задача', "+
                            " TO_DATE('03.03.3333 00:00:00', 'DD.MM.YYYY HH24:MI:SS'), "+
                            " 'MIN:1;HOUR:1;MONTH:1' "+
                            " ) ";
            template.execute(sql);
            return "OK";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String add_new_sub_task(BigDecimal task_id,String default_user) {
        try {
            String sql =
            "INSERT "+
            "INTO "+
            "STATBOY.ASR_AUTO_SUB_TASKS AAST "+
            "(AAST.ASR_AUTO_SUB_TASK_ID,AAST.ASR_AUTO_TASK_ID,AAST.ASR_AUTO_SUB_TASK_RUN_USER,AAST.ASR_AUTO_SUB_TASK_SQL) "+
            "VALUES "+
            "( " +
            " "+get_next_asr_auto_sub_tasks_id(task_id)+", "+
            " "+task_id+", "+
            " '"+default_user+"', "+
            " 'новый запрос'" +
            " ) ";

            template.execute(sql);
            return "OK";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String delete_task_by_id(String in_value) {
        try {
            String sql =
                    "DELETE "+
                    "FROM STATBOY.ASR_AUTO_TASKS AAT "+
                    "WHERE AAT.ASR_AUTO_TASK_ID = "+in_value+" ";
            template.execute(sql);
            return "OK";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String delete_sub_task_by_id (String in_sub_task_id,Integer in_task_id){
        try {
            String sql =
                " DELETE "+
                " FROM STATBOY.ASR_AUTO_SUB_TASKS AAST "+
                " WHERE AAST.ASR_AUTO_TASK_ID = "+in_task_id+" "+
                " AND AAST.ASR_AUTO_SUB_TASK_ID = "+in_sub_task_id;
            template.execute(sql);
            return "OK";

        }
        catch (Exception e){
            return e.getMessage();
        }
    }


}
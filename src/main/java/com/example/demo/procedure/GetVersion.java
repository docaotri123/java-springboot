package com.example.demo.procedure;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class GetVersion {

    private final JdbcTemplate jdbcTemplate;

    public GetVersion(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String getVersion() {
        String sql = "SELECT version()";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public void callInsertLogsV1(String logLevel, String systemId) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("insert_logs")
                .withCatalogName(null)
                .declareParameters(
                        new SqlParameter("p_log_level", Types.VARCHAR),
                        new SqlParameter("p_system_id", Types.VARCHAR),
                        new SqlOutParameter("po_err_msg", Types.VARCHAR)
                );

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_log_level", logLevel);
        inParams.put("p_system_id", systemId);

        Map<String, Object> outParams = simpleJdbcCall.execute(Map.class, inParams);

        String poErrMsg = (String) outParams.get("po_err_msg");
        System.out.println("OUT parameter value: " + poErrMsg);
    }

    public void callInsertLogs(String logLevel, String systemId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("insert_logs")
                .declareParameters(
                        new SqlParameter("p_log_level", Types.VARCHAR),
                        new SqlParameter("p_system_id", Types.VARCHAR),
                        new SqlOutParameter("po_err_msg", Types.VARCHAR)
                );

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_log_level", logLevel);
        inParams.put("p_system_id", systemId);

        Map<String, Object> outParams = jdbcCall.execute(inParams);

        String errMsg = (String) outParams.get("po_err_msg");
        System.out.println(errMsg);
    }


    public void callInsertLogsV2(String logLevel, String systemId) {
        String sql = "CALL insert_logs(?, ?, ?)";

        SqlParameter logLevelParam = new SqlParameter(Types.VARCHAR);
        SqlParameter systemIdParam = new SqlParameter(Types.VARCHAR);
        SqlOutParameter errParam = new SqlOutParameter("po_err_msg", Types.VARCHAR);

        Map<String, Object> result = jdbcTemplate.call(con -> {
            CallableStatement cs = con.prepareCall(sql);
            cs.setString(1, logLevel);
            cs.setString(2, systemId);
            cs.registerOutParameter(3, Types.VARCHAR);
            return cs;
        }, Arrays.asList(logLevelParam, systemIdParam, errParam));

        String poErrMsg = (String) result.get("po_err_msg");
        System.out.println("OUT parameter value: " + poErrMsg);
    }

}

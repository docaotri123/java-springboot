package com.example.demo.procedure;

import com.example.demo.mapper.PersonRowMapper;
import com.example.demo.models.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
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
                .declareParameters(
                        new SqlParameter("p_log_level", Types.VARCHAR),
                        new SqlParameter("p_system_id", Types.VARCHAR),
                        new SqlOutParameter("po_err_msg", Types.VARCHAR)
                );
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("p_log_level", logLevel);
        mapSqlParameterSource.addValue("p_system_id", systemId);

        Map<String, Object> outParams = simpleJdbcCall.execute(mapSqlParameterSource);

        String poErrMsg = (String) outParams.get("po_err_msg");
        System.out.println("OUT parameter value: " + poErrMsg);
    }

    @Transactional
    public List<Person> callInsertLogsAndPersons(String logLevel, String systemId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("insert_logs_and_get_persons")
                .declareParameters(
                        new SqlParameter("p_log_level", Types.VARCHAR),
                        new SqlParameter("p_system_id", Types.VARCHAR),
                        new SqlOutParameter("po_err_msg", Types.VARCHAR),
                        new SqlOutParameter("po_persons", Types.OTHER)
                );

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("p_log_level", logLevel);
        mapSqlParameterSource.addValue("p_system_id", systemId);

        jdbcCall.returningResultSet("po_persons", new PersonRowMapper());

        Map<String, Object> outParams = jdbcCall.execute(mapSqlParameterSource);

        @SuppressWarnings("unchecked")
        List<Person> people = (List<Person>) outParams.get("po_persons");
        String errMsg = (String) outParams.get("po_err_msg");
        System.out.println(errMsg);

        return people;
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

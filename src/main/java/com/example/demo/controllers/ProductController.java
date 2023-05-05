package com.example.demo.controllers;

import com.example.demo.models.Person;
import com.example.demo.procedure.GetVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "v1/products")
public class ProductController {

    @Autowired
    GetVersion getVersion;

    @Autowired
    private DataSource dataSource;

    @GetMapping("")
    List<Person> products() throws SQLException {
//        getVersion.callInsertLogsV2("A", "ACB");

//        return getVersion.getVersion();

        return getVersion.callInsertLogsAndPersons("A", "ABC");
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> getLogsAndPersons() throws SQLException {
        Map<String, Object> resultMap = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall("CALL insert_logs_and_get_persons(?,?,?,?)")) {

            // Set IN parameters
            callableStatement.setString(1, "INFO");
            callableStatement.setString(2, "system123");

            // Register OUT parameters
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.REF_CURSOR);

            callableStatement.execute();

            // Get OUT parameters
            String errorMessage = callableStatement.getString(3);
            ResultSet resultSet = (ResultSet) callableStatement.getObject(4);

            List<Person> personList = new ArrayList<>();
            while (resultSet.next()) {
                Person person = new Person(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("email"));
                personList.add(person);
            }

            resultMap.put("errorMessage", errorMessage);
            resultMap.put("persons", personList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(resultMap);
    }

}

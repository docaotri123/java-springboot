package com.example.demo.mapper;

import com.example.demo.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Person(
                Integer.parseInt(rs.getString("person_id")),
                rs.getString("first_name"),
                rs.getString("last_name")
        );
    }
}

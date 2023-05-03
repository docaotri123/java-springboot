package com.example.demo.controllers;

import com.example.demo.procedure.GetVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(path = "v1/products")
public class ProductController {

    @Autowired
    GetVersion getVersion;

    @GetMapping("")
    String products() throws SQLException {
//        getVersion.callInsertLogsV2("A", "ACB");
        getVersion.callInsertLogsV1("A", "ABC");

        return getVersion.getVersion();
    }
}

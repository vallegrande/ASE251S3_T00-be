package com.ValleGrande.ElserManuel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class TestController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/test-db")
    public String test() throws Exception {
        return dataSource.getConnection().getMetaData().getDatabaseProductName();
    }
}
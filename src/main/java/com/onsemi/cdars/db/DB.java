package com.onsemi.cdars.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Repository
public class DB extends SpringBeanAutowiringSupport {

    @Autowired
    private DataSource dataSource;

    public Connection getConnection() {
        Connection conn;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return conn;
    }
    
    public DataSource getDataSource() {
        return dataSource;
    }
}

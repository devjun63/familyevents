package com.familyevents.dbTest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;


public class MySQLConnectionTest {

    // MySQL Connector 의 클래스. DB 연결 드라이버 정의
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // DB 경로
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/familyevents?serverTimezone=Asia/Seoul&useSSL=false&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "familyevents";
    private static final String PASSWORD = "error404!";

    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection() throws Exception {
        // DBMS에게 DB 연결 드라이버의 위치를 알려주기 위한 메소드
        Class.forName(DRIVER);
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnectionDataSource() throws Exception {

        try (Connection connection = dataSource.getConnection()){
            System.out.println(connection);
        } catch (Exception e){
            e.printStackTrace();
        }
    }



}

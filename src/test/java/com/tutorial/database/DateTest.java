package com.tutorial.database;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class DateTest {

    @Test
    void testDate() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        String sql = "INSERT INTO sample_time(sample_date, sample_time, sample_timestamp) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
        preparedStatement.setTime(2, new Time(System.currentTimeMillis()));
        preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    @Test
    void testDateQuery() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        String sql = "SELECT * FROM sample_time";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println("Date : " + resultSet.getDate("sample_date"));
            System.out.println("Time : " + resultSet.getTime("sample_time"));
            System.out.println("Timestamp : " + resultSet.getTimestamp("sample_timestamp"));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}

/*
*
* NOTE :
*
* Seperti kita ketahui, tipe tanggal dan waktu di database biasanya banyak, ada Date, Time dan ada juga Timestamp.
* Sedangkan di Java hanya terdapat tipe data java.util.Date.
* Agar bisa menghandle hal ini, terdapat class" turunan java.util.Date di java.sql yang bernama Date, Time dan Timestamp.
* Sesuai dengan namanya, class" tersebut di gunakan sebagai representasi di Java.
* Secara otomatis JDBC bisa melakukan konversi tipe data tersebut dari database menjadi object di Java.
*
* */
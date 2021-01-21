package com.tutorial.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementTest {

    @Test
    void testCreateStatement() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

        statement.close();
        connection.close();
    }

    @Test
    void testExecuteUpdate() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

        String sql = "INSERT INTO customers (id, name, email) " +
                "VALUES ('Dua', 'Nurhamim', 'nurhamim@test.com')";
        int update = statement.executeUpdate(sql);
        System.out.println(update);

        statement.close();
        connection.close();
    }

    @Test
    void testExecuteDelete() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

        String sql = "DELETE FROM customers";
        int impact = statement.executeUpdate(sql);
        System.out.println(impact);

        statement.close();
        connection.close();
    }

    @Test
    void testExecuteQuery() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM customers";
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.close();
        statement.close();
        connection.close();
    }
}

/*
* NOTE :
*
* Connection adalah Object yang bertugas sebagai jembatan koneksi dari aplikasi kita ke database,
  untuk mengirim perintah SQL kita bisa menggunakan beberapa object lain, salah satunya adalah Statement.
* java.sql.Statement/Statement -> interface yang bisa kita gunakan untuk mengirim SQL ke database, sekaligus menerima response data dari database.
* Untuk membuat Statement kita bisa menggunakan method createStatement() milik Connection.
* Method executeUpdate() -> digunakan untuk mengirim perintah SQL INSERT, UPDATE, DELETE atau apapun yang tidak membutuhkan result.
* Method executeUpdate() mengembalikan return int, dimana biasanya mengembalikan berapa banyak record di database yang terkena impact perintah SQL kita,
  misal mengirim perintah UPDATE, berapa banyak record yang ter-update misalnya.
* Method executeQuery() -> digunakan untuk mengirim perintah SQL yang mengembalikan data.
* Method executeQuery() akan mengembalikan object java.sql.ResultSet, yaitu berisikan data" hasil query SQL yang kita kirimkan.
*
* */
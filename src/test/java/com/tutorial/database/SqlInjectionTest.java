package com.tutorial.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlInjectionTest {

    @Test
    void tesSqlInjection() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

//        Contoh SQL Injection
//        String username = "admin'; #";
//        String password = "passwordnyadisalahkan";

        String username = "admin";
        String password = "admin";

        String sql = "SELECT * FROM admin WHERE username = '"+ username +"' AND  password = '"+ password +"'";
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            // Sukses login
            System.out.println("Sukses login : " + resultSet.getString("username"));
        } else {
            // Gagal login
            System.out.println("Gagal login");
        }

        resultSet.close();
        statement.close();
        connection.close();
    }
}

/*
* NOTE :
*
* SQL Injection -> sebuah teknik yang menyalahgunakan sebuah celah keamanan yang terjadi dalam lapisan basis data sebuah aplikasi.
* Biasanya SQL Injection dilakukan dengan mengirim input dari user dengan perintah yang salah,
  sehingga menyebabkan hasil SQL yang kita buat menjadi tidak valid.
* Statement tidak didesain untuk bisa menangani SQL Injection.
* Untuk menghindari SQL Injection terdapat interface PreparedStatement, ini adalah jenis statement yang bisa menangani SQL Injection.
*
* */
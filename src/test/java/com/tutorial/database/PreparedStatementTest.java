package com.tutorial.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatementTest {

    @Test
    void testPreparedStatement() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        // SQL Injection
//        String username = "admin'; #";
//        String password = "salah";

        String username = "admin";
        String password = "admin";

        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            // Sukses login
            System.out.println("Sukses login : " + resultSet.getString("username"));
        } else {
            // Gagal login
            System.out.println("Gagal login");
        }

        preparedStatement.close();
        connection.close();
    }
}

/*
* NOTE :
*
* PreparedStatement merupakan turunan dari Statement, jadi apapun yang bisa dilakukan di Statement bisa juga dilakukan di PreparedStatement.
* Yang membedakan PreparedStatement dari Statement adalah PreparedStatement memiliki kemampuan untuk mengamankan input dari user sehingga aman dari serangan SQL Injection.
* PreparedStatement biasanya digunakan untuk sekali mengirim perintah SQL, jika ingin mengirim perintah SQL lagi, kita harus membuat PreparedStatement lagi.
* Untuk membuat PreparedStatement kita bisa menggunakan method preparedStatement() milik Connection.
* Untuk menerima input user, SQL yang kita buat harus diubah juga.
* Input dari user, perlu kita ubah menjadi ? (tanda tanya).
* Nanti ketika pembuatan object, kita bisa subtitusi datanya menggunakan setXxx(index, value)
  sesuai dengan tipe datanya. Misal setString(), setInt() dll.
*
*
 * */
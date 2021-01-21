package com.tutorial.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultSetTest {

    @Test
    void testExecuteQuery() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM customers";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");

            System.out.println(
                    String.join(", ", id, name, email)
            );
        }

        resultSet.close();
        statement.close();
        connection.close();
    }
}

/*
* NOTE :
*
* Cara kerja ResultSet adalah seperti cursor, dimana untuk maju kita menggunakan method next() dan untuk mundur kita bisa menggunakan method previous().
* Untuk mendapatkan data tiap kolom pada saat sekarang kita bisa berada di lokasi cursornya, kita bisa menggunakan banyak sekali method getXxx(column) di ResulSet.
* Kita bisa sesuaikan dengan tipe data kolom tersebut, misal getString(column), getInt(column) dll.
* */
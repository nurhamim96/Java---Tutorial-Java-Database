package com.tutorial.database;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class AutoIncrementTest {

    @Test
    void testAutoIncrement() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        String sql = "INSERT INTO comments(email, comment) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, "ibad@test.com");
        preparedStatement.setString(2, "hi");

        preparedStatement.executeUpdate();
        ResultSet resultSet =  preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            System.out.println("Id Comment : " + resultSet.getInt(1)); // karena resultnya  hanya 1 data, maka masukan masukan angka 1 pada parameternya.
        }

        preparedStatement.close();
        connection.close();
    }
}

/*
*
* NOTE :
*
* Kadang setelah melakuna insert data ke database yang memiliki primary key auto increment, kita ingin mendapatkan data id terbarunya.
* Di MySQL sebenarnya kita bisa melakukan query SELECT LAST_INSERT_ID(),
  namun berarti kita harus melakukan query ulang dengan Statement dan melakukan iterasi lagi dengan ResultSet.
* Untungnya di JDBC ada kemampuan untuk mendapatka auto generate data seperti auto increment dengan method getGenerateKeys() yang mengembalikan ResultSet.
* Selanjutnya kita bisa melakukan iterasi terhadap ResulSet tersebut.
* Secara default, Statement ataupun PreparedStatement tidak mengerti untuk mengambil auto generate key.
* Kita perlu memberi tahunya agar Statement atau PreparedStatement mengambil auto generate id secara otomatis.
* Untuk Statement, kita perlu memberi tahu ketika memanggil method executeUpdate(sql, Statement.RETURN_GENERATED_KEYS)
* Sedangkan untuk PreparedStatement, kita perlu memberitahu ketika membuat preparedStatement(sql, Statement.RETURN_GENERATED_KEYS)
* Setelah itu, untuk mendapatkan auto generate key, kita bisa menggunakan method getGeneratedKeys(),
  method ini akan error jika kita lupa mengirim parameter Statement.RETURN_GENERATED_KEYS.
*
*
* */
package com.tutorial.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchTest {

    @Test
    void testStatement() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

        String sql = "INSERT INTO comments(email, comment) VALUES ('ibad@test.com', 'hi')";

        for (int i = 0; i < 1000; i++) {
            statement.addBatch(sql);
        }

        statement.executeBatch();

        statement.close();
        connection.close();
    }

    @Test
    void testPreparedStatement() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        String sql = "INSERT INTO comments(email, comment) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < 1000; i++) {
            preparedStatement.clearParameters();
            preparedStatement.setString(1, "ibad@test.com");
            preparedStatement.setString(2, "hi");
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();

        preparedStatement.close();
        connection.close();
    }
}

/*
* NOTE :
*
* Secara default komunikasi antara database dan aplikasi Java kita adalah request dan response.
* Artinya, setiap kali kita mengirim perintah SQL maka aplikasi kita akan menunggu sampai database melakukan response dari perintah SQL tersebut.
* Proses tersebut kadang terlaly chatty kalo kita misal ingin mengirim data secara banyak ke database.
* Batch process -> proses mengirim perintah secara banyak sekaligus.
* Biasanya batch process dilakukan dalam kasus tertentu saja, misal kita ingin mengirim import data dari file excel ke database dengan jumlah jutaan.
* Biasanya dalam batch process, yang diutamakan adalah kecepatan,karena jika perintah SQL nya di execute satu" dan menunggu response satu", maka sudah pasti akan sangat lambat sekali.
* JDBC mendukung proses eksekusi perintah SQL secara batch di Statement ataupun di PreparedStatement.
* Di Statement terdapat method addBatch(), untuk menambahkan perintah ke proses batch.
* Sedangkan di PreparedStatement terdapat method addBatch() untuk menambahkan proses ke batch, lalu bisa gunakan method clearParameter() untuk menghapus parameter input user sebelumnya.
* Setelah proses batch selesai, untuk mengeksekusinya kita bisa menggunakan perintah executeBatch().
*
* */

/*
*
* PERINGATAN :
*
* Proses batch akan disimpan di memory sebelum dikirim ke database.
* Oleh karena itu, bijaklah membuat proses batch jangan terlalu banyak menambahkan batch, misal per 100 atau per 1000.
* Jika sudah mencapai 100 atau 1000, kita bisa mengirim batch tersebut menggunakan perintah executeBatch().
*
* */
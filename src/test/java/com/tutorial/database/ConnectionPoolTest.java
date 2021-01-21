package com.tutorial.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolTest {

    @Test
    void testHikariCP() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/belajar_java_database?serverTimezone=Asia/Jakarta");
        config.setUsername("root");
        config.setPassword("");

        // CONFIG CONNECTION POOL
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(60_000);
        config.setMaxLifetime(10 * 60_000);

        try {
            HikariDataSource dataSource = new HikariDataSource(config);
            Connection connection = dataSource.getConnection();
            connection.close();
            dataSource.close();
        } catch (SQLException exception) {
            Assertions.fail(exception);
        }

    }

    @Test
    void testUtil() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
    }
}

/*
* NOTE :
*
* Jika aplikasi sangat ketergantungan dengan database, maka membuka tutup koneksi setiap ada request sangatlah mahal harganya.
* Connection itu sangat lambat ketika pertama kali dibuat dan sangat mahal memakan memory.
* Oleh karena itu manajemen Connection secara manual sangat tidak direkomendasikan lebih disarankan menggunakan Connection Pool.
* Connection Pool -> konsep dimana dibandingkan kita membuat koneksi baru setiap ada request ke yang membutuhkan database.
* Lebih baik diawal kita buatkan banyak Connection terlebih dahulu, sehingga hanya lambat diawal ketika aplikasi berjalan.
* Selanjutnya ketika ada request yang butuh koneksi, kita hanya cukup menggunakan salah satu Connection dan setelah selesai kita kembalikan lagi Connectionnya ke Pool.
* Jika semua Connection sedang terpakai semua, ketika ada request yang butuh koneksi lagi,
  request tersebut diminta menunggu terlebih dahulu, dengan demikian penggunaan memory untuk Connection tidak akan terlalu bengkak.
* Connection Pool di JDBC direpresentasikan dengan interface javax.sql.DataSource
* Salah satu library Connection Pool yang sangat populer di Java adalah HikariCP
*
* */
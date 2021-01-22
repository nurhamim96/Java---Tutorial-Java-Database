package com.tutorial.database;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class MetaDataTest {

    @Test
    void testDatabaseMetaData() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        DatabaseMetaData databaseMetaData = connection.getMetaData();

        System.out.println(databaseMetaData.getDatabaseProductName());
        System.out.println(databaseMetaData.getDatabaseProductVersion());

        ResultSet resultSet = databaseMetaData.getTables("belajar_java_database", null, null, null);
        while (resultSet.next()) {
            String tableName = resultSet.getString("TABLE_NAME");
            System.out.println("Table : " + tableName);
        }
    }

    @Test
    void testParameterMetaData() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String sql = "INSERT INTO comments(email, comment) VALUES (?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setString(1, "ibad@test.com");
//        statement.setString(2, "hallo");

        ParameterMetaData parameterMetaData = statement.getParameterMetaData();

        System.out.println(parameterMetaData.getParameterCount());
//        System.out.println(parameterMetaData.getParameterType(1)); // Gak support

        statement.close();
        connection.close();
    }

    @Test
    void testResultSetMetaData() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
            System.out.println("Name : " + resultSetMetaData.getColumnName(i));
            System.out.println("Type : " + resultSetMetaData.getColumnType(i));
            System.out.println("Type Name : " + resultSetMetaData.getColumnTypeName(i));

            // CONTOH PENGECEKAN
            if (resultSetMetaData.getColumnType(i) == Types.VARCHAR) {
                System.out.println("INI VARCHAR");
            }
        }

        resultSet.close();
        statement.close();
        connection.close();
    }
}

/*
*
* NOTE :
*
* Ada banyak jenis metadata, seperti DatabaseMetaData, ParameterMetaData, ResultSetMetaData.
* DatabaseMetaData -> informasi seputar seluruh database yang kita gunakan.
* Misal : Nama database, Versi database, Table yang ada di database dll.
* Untuk membuat DatabaseMetaData kita bisa menggunakan method getMetaData() dari object Connection.
* ParameterMetadata -> informasi seputar parameter yang terdapat di PreparedStatement.
* Dengan ParameterMetaData kita bisa mendapatkan banyak informasi parameter yang bisa digunakan untuk input di PreparedStatement,
  seperti berapa banyak parameter, tipe data parameter dll.
* Namun perlu diperhatikan, beberapa Driver mungkin tidak mendukung untuk mendapatkan jenis tipe parameter di ParameterMetaData.
* ResultSetMetaData -> informasi seputar hasil ResultSet
* Dengan ResultSetMetaData kita bisa mendapatkan informasi tentang jumlah kolom, nama kolom, tipe data kolom dll.
*
 * */
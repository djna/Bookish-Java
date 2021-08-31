package org.softwire.training.bookish;

import org.jdbi.v3.core.Jdbi;

import java.sql.*;
import java.util.Properties;


public class Main {

    public static void main(String[] args) throws SQLException {
        String hostname = "localhost:3306";
        String database = "djna";
        String user = "booker";
        String password = "booker";
        String connectionString = "jdbc:mysql://" + hostname
                + "/djna";
                //"&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT&useSSL=false";

        Properties connectionProps = new Properties();
        connectionProps.put("user", user);
        connectionProps.put("password", password);
        connectionProps.setProperty("useSSL", "false");

        jdbcMethod(connectionString, connectionProps);
        //jdbiMethod(connectionString);
    }

    private static void jdbcMethod(String connectionString, Properties connectionProps) throws SQLException {
        System.out.println("JDBC method...");

        // TODO: print out the details of all the books (using JDBC)
        // See this page for details: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html

        Connection connection = DriverManager.getConnection(connectionString, connectionProps);
        System.out.println("JDBC connected ...");
        String query = "select id, title, author from books";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");

                    System.out.printf("%d: %s by %s\n", id, title, author);
                }
            } catch (SQLException e) {
                System.out.printf("Query Exception " + e);
            }


        connection.close();
        System.out.println("JDBC connection closed.");

    }

    private static void jdbiMethod(String connectionString) {
        System.out.println("\nJDBI method...");

        // TODO: print out the details of all the books (using JDBI)
        // See this page for details: http://jdbi.org
        // Use the "Book" class that we've created for you (in the models.database folder)

        Jdbi jdbi = Jdbi.create(connectionString);



    }
}

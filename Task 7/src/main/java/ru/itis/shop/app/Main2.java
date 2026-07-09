package ru.itis.shop.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/shop_db", "postgres", "qwerty007")) {

            // insert into account(email, password) values('mail@gmail.com', 'qwerty007');

            String email = scanner.nextLine();
            String password = scanner.nextLine();

            try (Statement statement = connection.createStatement()) {
                String sql = "insert into account(email, password) values('" + email + "','" + password + "');";

                System.out.println(sql);

                int affectedRowsCount = statement.executeUpdate(sql);

                if (affectedRowsCount != 1) {
                    throw new SQLException("Can't insert");
                }

                System.out.println("Добавлено");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

/*
temp@mail.com', 'temp'); drop table orders; insert into account(email, password) values ('temp
password
insert into account(email, password) values('temp@mail.com', 'temp'); drop table orders; insert into account(email, password) values ('temp','password');
Добавлено
 */

package com.example.bdtema.repositories;

import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class PizzaRepository {

    public ResultSet getAllPizzas(Connection connection) throws SQLException {

        String query = "select * from pizza";
        Statement statement = connection.createStatement();

        return statement.executeQuery(query);
    }
}

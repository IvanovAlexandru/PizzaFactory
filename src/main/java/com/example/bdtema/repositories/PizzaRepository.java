package com.example.bdtema.repositories;

import com.example.bdtema.models.PizzaModel;
import com.example.bdtema.models.SauceModel;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PizzaRepository {

    public List<PizzaModel> getAllPizzas(Connection connection) throws SQLException {

        String query = "select * from pizza";
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        List<PizzaModel> ll = new ArrayList<>();

        while (resultSet.next()){
            int i = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String imagePath = resultSet.getString("image_path");

            PizzaModel pizza = new PizzaModel(i,name,description,imagePath);
            ll.add(pizza);
        }
        return ll;
    }

    public List<SauceModel> getAllSauces(Connection connection) throws SQLException {
        String query = "select * from sauce";

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        List<SauceModel> ll = new ArrayList<>();

        while (resultSet.next()){
            int i = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String imagePath = resultSet.getString("image_path");

            SauceModel pizza = new SauceModel(i,name,description,imagePath);
            ll.add(pizza);
        }
        return ll;
    }
}

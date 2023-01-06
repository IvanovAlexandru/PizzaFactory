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
            int price = resultSet.getInt("price");

            PizzaModel pizza = new PizzaModel(i,name,description,imagePath,price);
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
            int price = resultSet.getInt("price");

            SauceModel pizza = new SauceModel(i,name,description,imagePath,price);
            ll.add(pizza);
        }
        return ll;
    }

    public PizzaModel findPizzaById(Connection connection,Integer id) throws SQLException {

        String query = "select * from pizza where id = " + id + ";";
        Statement statement = connection.createStatement();
        ResultSet pizza = statement.executeQuery(query);

        pizza.next();
        return new PizzaModel(pizza.getInt("id"),pizza.getString("name"),pizza.getString("description")
                ,pizza.getString("image_path"),pizza.getInt("price"));

    }
    public SauceModel findSauceById(Connection connection,Integer id) throws SQLException {

        String query = "select * from sauce where id = " + id + ";";
        Statement statement = connection.createStatement();
        ResultSet sauce = statement.executeQuery(query);

        sauce.next();
        return new SauceModel(sauce.getInt("id"),sauce.getString("name"),sauce.getString("description")
                ,sauce.getString("image_path"),sauce.getInt("price"));

    }

    public List<PizzaModel> getPizzaByKeyword(Connection connection,String keyword) throws SQLException {

        String query = "select * from pizza where name ilike '%" + keyword + "%';";
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        List<PizzaModel> ll = new ArrayList<>();

        while (resultSet.next()){
            int i = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String imagePath = resultSet.getString("image_path");
            int price = resultSet.getInt("price");

            PizzaModel pizza = new PizzaModel(i,name,description,imagePath,price);
            ll.add(pizza);
        }
        return ll;
    }

    public List<SauceModel> getSauceByKeyword(Connection connection,String keyword) throws SQLException {

        String query = "select * from sauce where name ilike '%" + keyword + "%';";
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        List<SauceModel> ll = new ArrayList<>();

        while (resultSet.next()){
            int i = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String imagePath = resultSet.getString("image_path");
            int price = resultSet.getInt("price");

            SauceModel sauce = new SauceModel(i,name,description,imagePath,price);
            ll.add(sauce);
        }
        return ll;
    }
}

package com.example.bdtema.repositories;

import com.example.bdtema.models.PizzaModel;
import com.example.bdtema.models.SauceModel;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.nimbus.State;
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
            int type = resultSet.getInt("type");
            PizzaModel pizza = new PizzaModel(i,name,description,imagePath,price,type);
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
                ,pizza.getString("image_path"),pizza.getInt("price"),pizza.getInt("type"));

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
            int type = resultSet.getInt("type");

            PizzaModel pizza = new PizzaModel(i,name,description,imagePath,price,type);
            ll.add(pizza);
        }
        return ll;
    }

    public void addToBucketList(Connection connection,Integer pizzaId,Integer userId) throws SQLException {

        String query = "insert into user_menu(user_id,menu_id) values (" + userId + "," + pizzaId + ");";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);

    }

    public void deleteFromBucket(Connection connection,Integer bucketId,Integer userId) throws SQLException {

        String query = "delete from user_menu where user_id = " + userId + " and menu_id = " + bucketId + ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);

    }
    public List<PizzaModel> getMenuForUser(Connection connection,Integer userId) throws SQLException {

        String query = "select * from user_menu where user_id = " + userId + ";";
        Statement statement = connection.createStatement();
        ResultSet bucket = statement.executeQuery(query);

        List<PizzaModel> bucketList = new ArrayList<>();

        while (bucket.next()){
            int id = bucket.getInt("menu_id");

            PizzaModel pizzaModel = findPizzaById(connection,id);
            bucketList.add(pizzaModel);

        }
        return bucketList;
    }
}

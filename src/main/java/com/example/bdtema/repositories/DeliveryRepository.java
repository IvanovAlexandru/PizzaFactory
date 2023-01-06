package com.example.bdtema.repositories;

import com.example.bdtema.models.DeliveryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class DeliveryRepository {

    private final UserRepository userRepository;
    @Autowired
    public DeliveryRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addDelivery(Connection connection, DeliveryModel deliveryModel) throws SQLException {

        String query = "insert into delivery(delivery_id,name,address,payment_method,time) values(" + "nextval(" + "'" + "seq_deliv_id'" +
                ")," + "'"
                + deliveryModel.getName() + "'" + "," + "'"
                + deliveryModel.getAddress() + "'" + ","
                + deliveryModel.getPayment() + "," + "'"
                + LocalDateTime.now() +  "'" + ");";
        String getId = "select * from delivery where address = '" + deliveryModel.getAddress() +"'" + ";";

        Statement statement = connection.createStatement();

        statement.executeUpdate(query);
        ResultSet id = statement.executeQuery(getId);
        id.next();
        deliveryModel.setId(id.getInt("delivery_id"));

    }

    public void addDeliveryToUser(Connection connection,String user,DeliveryModel deliveryModel) throws SQLException {

        Integer userId = userRepository.getIdByUsername(connection,user);
        String query = "insert into user_deliveries (user_id,delivery_id) values (" + userId + ","
                + deliveryModel.getId() + ");";
        Statement statement = connection.createStatement();
        
        statement.executeUpdate(query);
    }

    public void deleteDeliveryFromUser(Connection connection,String user,Integer id) throws SQLException {
        Integer userId = userRepository.getIdByUsername(connection,user);
        String query = "delete from user_deliveries where user_id = " + userId
                + " and delivery_id = " + id + ";";
        Statement statement = connection.createStatement();

        statement.executeUpdate(query);
    }
    public DeliveryModel getDeliveryById(Connection connection,Integer id) throws SQLException {

        String query = "select * from delivery where delivery_id = " + id + ";";
        Statement statement = connection.createStatement();
        ResultSet delivery = statement.executeQuery(query);


        if(delivery.next()){
            return new DeliveryModel(delivery.getInt("delivery_id"),
                    delivery.getString("name"),
                    delivery.getString("address"),
                    delivery.getInt("payment_method"),
                    delivery.getDate("time"));
        }
        else {
            return new DeliveryModel();
        }

    }
    public List<DeliveryModel> getDeliveriesForUser(Connection connection,String user) throws SQLException {

        List<DeliveryModel> deliveryModels = new ArrayList<>();
        Integer userId = userRepository.getIdByUsername(connection,user);

        String query = "select * from user_deliveries where user_id = " + userId + ";";
        Statement statement = connection.createStatement();
        ResultSet deliveries = statement.executeQuery(query);


        while (deliveries.next()){
            Integer id = deliveries.getInt("delivery_id");

            deliveryModels.add(getDeliveryById(connection,id));
        }

        return deliveryModels;
    }

    public void deleteDeliveryById(Connection connection,Integer id) throws SQLException {

        String query = "delete from delivery where delivery_id = " + id + ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);

    }

    public void updateDelivery(Connection connection,DeliveryModel deliveryModel) throws SQLException {

        String query = "update delivery set name = '" + deliveryModel.getName() + "',"
                + " address = '" + deliveryModel.getAddress() + "',"
                + "payment_method = " + deliveryModel.getPayment()
                + " where delivery_id = " + deliveryModel.getId() + ";";

        Statement statement = connection.createStatement();
        statement.executeUpdate(query);

    }

}

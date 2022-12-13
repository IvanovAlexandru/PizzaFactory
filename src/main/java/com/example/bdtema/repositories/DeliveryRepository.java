package com.example.bdtema.repositories;

import com.example.bdtema.models.DeliveryModel;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class DeliveryRepository {

    public void addDelivery(Connection connection, DeliveryModel deliveryModel) throws SQLException {

        String query = "insert into delivery(name,address,payment_method) values(" + "'"
                + deliveryModel.getName() + "'" + "," + "'"
                + deliveryModel.getAddress() + "'" + ","
                + deliveryModel.getPayment() + ")";
        Statement statement = connection.createStatement();

        statement.executeUpdate(query);
    }
}

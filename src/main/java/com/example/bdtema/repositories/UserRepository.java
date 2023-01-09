package com.example.bdtema.repositories;

import com.example.bdtema.models.PizzaModel;
import com.example.bdtema.models.UserModel;
import org.springframework.stereotype.Repository;

import java.net.ConnectException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepository {

    public void saveUser(Connection connection, UserModel userModel,String encryptedPass) throws SQLException {

        String query = "insert into users(id,email,password) values(" + "nextval(" +"'" + "seq_id'" +
                ")," + "'" + userModel.getEmail()
                + "','" + encryptedPass + "'" + ");";
        Statement statement = connection.createStatement();

        statement.executeUpdate(query);

    }

    public String encryptPass(String password){

        String encryptedPassword = null;

        try{
            MessageDigest m = MessageDigest.getInstance("MD5");

            m.update(password.getBytes());

            byte[] bytes = m.digest();

            StringBuilder s = new StringBuilder();

            for (byte aByte : bytes) {
                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            encryptedPassword = s.toString();
        }
        catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

        }

        return encryptedPassword;
    }

    public boolean findUser(Connection connection,UserModel userModel) throws SQLException {

        String query = "select * from users where email = " + "'" + userModel.getEmail() + "'" + " and " + " password = " + "'" + encryptPass(userModel.getPassword()) + "';";

        Statement statement = connection.createStatement();

        ResultSet verifyUser = statement.executeQuery(query);

        if (!verifyUser.next()){
            return false;
        }
        else return true;
    }

    public Integer getIdByUsername(Connection connection,String user) throws SQLException {

        String query = "select * from users where email = '"+ user + "'" + ";";

        Statement statement = connection.createStatement();
        ResultSet id = statement.executeQuery(query);
        id.next();
        return id.getInt("id");
    }

}

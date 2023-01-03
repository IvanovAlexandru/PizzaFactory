package com.example.bdtema.controllers;

import com.example.bdtema.models.DeliveryModel;
import com.example.bdtema.models.UserModel;
import com.example.bdtema.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Controller
public class UserController {

    private static final String url = "jdbc:postgresql://localhost:5432/pizza";
    private static final String uname = "postgres";
    private static final String password = "admin";
    private static final Connection con;

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    static {
        try {
            con = DriverManager.getConnection(url,uname,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/login")
    public String getLoginPage(Model model,UserModel userModel){

        userModel = new UserModel();
        model.addAttribute("userModel",userModel);

        return "login";
    }

    @PostMapping("/loginPage")
    public String loginToIndex(@ModelAttribute("userModel") UserModel userModel) throws SQLException {

        //String encryptedPass = userRepository.encryptPass(userModel.getPassword());

        boolean verifyUser = userRepository.findUser(con,userModel);

        if(verifyUser){
            return "redirect:/home";
        }
        else {
            return "redirect:/login";
        }
    }
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("userModel") UserModel userModel) throws SQLException {

        String encryptedPass = userRepository.encryptPass(userModel.getPassword());

        userRepository.saveUser(con,userModel,encryptedPass);

        return "redirect:/login";
    }
    @GetMapping("/register")
    public String getRegisterPage(Model model, UserModel userModel){

        userModel = new UserModel();
        model.addAttribute("userModel",userModel);

        return "register";
    }
}

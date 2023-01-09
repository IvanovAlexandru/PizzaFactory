package com.example.bdtema.controllers;

import com.example.bdtema.models.PizzaModel;
import com.example.bdtema.models.SauceModel;
import com.example.bdtema.repositories.PizzaRepository;
import com.example.bdtema.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.bdtema.controllers.UserController.userName;


@Controller
public class PizzaController {

    private final PizzaRepository pizzaRepository;
    private final UserRepository userRepository;

    private static final String url = "jdbc:postgresql://localhost:5432/pizza";
    private static final String uname = "postgres";
    private static final String password = "admin";
    private static final Connection con;

    static {
        try {
            con = DriverManager.getConnection(url,uname,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public PizzaController(PizzaRepository pizzaRepository, UserRepository userRepository) {
        this.pizzaRepository = pizzaRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String indexPage(Model model,String keyword) throws SQLException {

        model.addAttribute("userName",userName);

        if(keyword != null){
            List<PizzaModel> allPizzas = pizzaRepository.getPizzaByKeyword(con,keyword);
            model.addAttribute("allPizzas",allPizzas);
        }
        else{
            List<PizzaModel> allPizzas = pizzaRepository.getAllPizzas(con);
            model.addAttribute("allPizzas",allPizzas);
        }

        if(userName == null){
            return "redirect:/login";
        }
        else {
            return "index";
        }

    }
    @GetMapping("/menu")
    public String menuPage(Model model,String keyword) throws SQLException {

        if(keyword != null){
            List<PizzaModel> allPizzas = pizzaRepository.getPizzaByKeyword(con,keyword);
            model.addAttribute("allPizzas",allPizzas);
        }
        else {
            List<PizzaModel> allPizzas = pizzaRepository.getAllPizzas(con);
            model.addAttribute("allPizzas",allPizzas);
        }
        model.addAttribute("userName",userName);

        if(userName == null){
            return "redirect:/login";
        }
        else {
            return "menu";
        }
    }
    @GetMapping("/contacts")
    public String contactsPage(Model model){

        model.addAttribute("userName",userName);

        if(userName == null){
            return "redirect:/login";
        }
        else {
            return "contacts";
        }
    }
    @GetMapping("/addPizzaToBucket/{id}")
    public String addPizzaToBucket(@PathVariable Integer id) throws SQLException {


        pizzaRepository.addToBucketList(con,id,userRepository.getIdByUsername(con,userName));

        if(userName == null){
            return "redirect:/login";
        }
        else {
            return "redirect:/deliveries";
        }
    }

    @GetMapping("/sauces")
    public String saucesPage(Model model,String keyword) throws SQLException {

        if(keyword != null){
            List<PizzaModel> sauceModels = pizzaRepository.getPizzaByKeyword(con,keyword);
            model.addAttribute("allSauces",sauceModels);
        }
        else {
            List<PizzaModel> sauceModels = pizzaRepository.getAllPizzas(con);
            model.addAttribute("allSauces",sauceModels);
        }

        model.addAttribute("userName",userName);

        if(userName == null){
            return "redirect:/login";
        }
        else {
            return "sauce";
        }
    }

}

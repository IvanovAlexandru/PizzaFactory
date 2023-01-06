package com.example.bdtema.controllers;

import com.example.bdtema.models.PizzaModel;
import com.example.bdtema.models.SauceModel;
import com.example.bdtema.repositories.PizzaRepository;
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

    public static List<SauceModel> sauceModels = new ArrayList<>();
    public static List<PizzaModel> pizzaModels = new ArrayList<>();

    private final PizzaRepository pizzaRepository;

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
    public PizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping("/")
    public String indexPage(Model model,String keyword) throws SQLException {

        model.addAttribute("userName",userName);

        if(keyword != null){
            List<PizzaModel> allPizzas = pizzaRepository.getPizzaByKeyword(con,keyword);
            model.addAttribute("allPizzas",allPizzas);
            List<SauceModel> sauceModels = pizzaRepository.getSauceByKeyword(con,keyword);
            model.addAttribute("allSauces",sauceModels);
        }
        else{
            List<PizzaModel> allPizzas = pizzaRepository.getAllPizzas(con);
            model.addAttribute("allPizzas",allPizzas);
            List<SauceModel> sauceModels = pizzaRepository.getAllSauces(con);
            model.addAttribute("allSauces",sauceModels);
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

        pizzaModels.add(pizzaRepository.findPizzaById(con,id));

        if(userName == null){
            return "redirect:/login";
        }
        else {
            return "redirect:/deliveries";
        }
    }
    @GetMapping("/addSauceToBucket/{id}")
    public String addSauceToBucket(@PathVariable Integer id) throws SQLException {

        sauceModels.add(pizzaRepository.findSauceById(con,id));

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
            List<SauceModel> sauceModels = pizzaRepository.getSauceByKeyword(con,keyword);
            model.addAttribute("allSauces",sauceModels);
        }
        else {
            List<SauceModel> sauceModels = pizzaRepository.getAllSauces(con);
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

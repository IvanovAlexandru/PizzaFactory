package com.example.bdtema.controllers;

import com.example.bdtema.models.DeliveryModel;
import com.example.bdtema.models.PizzaModel;
import com.example.bdtema.models.SauceModel;
import com.example.bdtema.repositories.DeliveryRepository;
import com.example.bdtema.repositories.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.*;
import java.util.List;


@Controller
public class PizzaController {


    private final PizzaRepository pizzaRepository;

    private final DeliveryRepository deliveryRepository;
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
    public PizzaController(PizzaRepository pizzaRepository, DeliveryRepository deliveryRepository) {
        this.pizzaRepository = pizzaRepository;
        this.deliveryRepository = deliveryRepository;
    }

    @GetMapping("/home")
    public String indexPage(Model model) throws SQLException {


        return "index";

    }
    @GetMapping("/menu")
    public String menuPage(Model model) throws SQLException {

        List<PizzaModel> allPizzas = pizzaRepository.getAllPizzas(con);
        model.addAttribute("allPizzas",allPizzas);

        return "menu";
    }
    @GetMapping("/delivery")
    public String deliveryPage(Model model){

        DeliveryModel deliveryModel = new DeliveryModel();
        model.addAttribute("deliveryModel",deliveryModel);

        return "delivery";
    }

    @PostMapping("/newDelivery")
    public String deliveryTest(@ModelAttribute("deliveryModel") DeliveryModel deliveryModel) throws SQLException {

        deliveryRepository.addDelivery(con,deliveryModel);

        return "redirect:/menu";
    }
    @GetMapping("/contacts")
    public String contactsPage(Model model){
        return "contacts";
    }

    @GetMapping("/sauces")
    public String saucesPage(Model model) throws SQLException {

        List<SauceModel> sauceModels = pizzaRepository.getAllSauces(con);
        model.addAttribute("allSauces",sauceModels);

        return "sauce";
    }


}

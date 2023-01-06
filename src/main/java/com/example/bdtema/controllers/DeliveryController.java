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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.example.bdtema.controllers.PizzaController.pizzaModels;
import static com.example.bdtema.controllers.PizzaController.sauceModels;
import static com.example.bdtema.controllers.UserController.userName;

@Controller
public class DeliveryController {


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
    public DeliveryController(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @GetMapping("/delivery")
    public String deliveryPage(Model model){

        DeliveryModel deliveryModel = new DeliveryModel();
        model.addAttribute("deliveryModel",deliveryModel);
        model.addAttribute("userName",userName);

        if(userName == null){
            return "redirect:/login";
        }
        else {
            return "delivery";
        }
    }

    @PostMapping("/newDelivery")
    public String deliveryTest(@ModelAttribute("deliveryModel") DeliveryModel deliveryModel) throws SQLException {

        deliveryRepository.addDelivery(con,deliveryModel);
        deliveryRepository.addDeliveryToUser(con,userName,deliveryModel);
        return "redirect:/deliveries";
    }

    @GetMapping("/deliveries")
    public String deliveriesPage(Model model) throws SQLException {

        model.addAttribute("userName",userName);
        model.addAttribute("userDelivery",deliveryRepository.getDeliveriesForUser(con,userName));
        model.addAttribute("bucketPizzas",pizzaModels);
        model.addAttribute("bucketSauces",sauceModels);

        Integer sumOfList = 0;
        for (PizzaModel pizza : pizzaModels){
            sumOfList += pizza.getPrice();
        }
        for (SauceModel sauce : sauceModels){
            sumOfList += sauce.getPrice();
        }

        model.addAttribute("sumOfBucket",sumOfList);

        if(userName == null){
            return "redirect:/login";
        }
        else {
            return "deliveries";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Integer id) throws SQLException {

        deliveryRepository.deleteDeliveryById(con,id);
        deliveryRepository.deleteDeliveryFromUser(con,userName,id);

        return "redirect:/deliveries";
    }
    @GetMapping("/edit/{id}")
    public String getEditDelivery(Model model,@PathVariable Integer id) throws SQLException {

        model.addAttribute("deliveryModel",deliveryRepository.getDeliveryById(con,id));

        if(userName == null){
            return "redirect:/login";
        }
        else {
            return "edit_address";
        }
    }
    @PostMapping("/delivery/{id}")
    public String editDelivery(@PathVariable Integer id,@ModelAttribute("delivery") DeliveryModel deliveryModel,Model model) throws SQLException {

        DeliveryModel existingDelivery = deliveryRepository.getDeliveryById(con,id);
        existingDelivery.setId(deliveryModel.getId());
        existingDelivery.setName(deliveryModel.getName());
        existingDelivery.setAddress(deliveryModel.getAddress());
        existingDelivery.setPayment(deliveryModel.getPayment());
        existingDelivery.setDate(deliveryModel.getDate());

        deliveryRepository.updateDelivery(con,existingDelivery);

        return "redirect:/deliveries";
    }
}

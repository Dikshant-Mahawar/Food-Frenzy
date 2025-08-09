package com.example.demo.controllers;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.entities.Orders;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.services.OrderServices;
import com.example.demo.services.ProductServices;
import com.example.demo.services.UserServices;

@Controller
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @Autowired
    private ProductServices productServices;

    @Autowired
    private UserServices userServices;

    // Step 2: Show order form for a product
    @GetMapping("/order/{productId}")
    public String showOrderForm(@PathVariable int productId, Model model) {
        Product product = productServices.getProduct(productId);
        if (product == null) {
            return "redirect:/products";  // Product not found, redirect back
        }
        model.addAttribute("product", product);
        model.addAttribute("order", new Orders());
        return "OrderForm";  // create this Thymeleaf template
    }

    // Step 3: Handle order form submission
    @PostMapping("/order/submit")
    public String submitOrder(@ModelAttribute Orders order, Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        String email = principal.getName();
        User user = userServices.getUserByEmail(email);
        if (user == null) {
            return "redirect:/login";
        }

        order.setUser(user);
        order.setTotalAmmout(order.getoPrice() * order.getoQuantity());
        order.setOrderDate(new Date());

        orderServices.saveOrder(order);

        model.addAttribute("amount", order.getTotalAmmout());
        return "OrderSuccess";
    }

    
}

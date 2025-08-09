package com.example.demo.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.User;
import com.example.demo.services.UserServices;

@Controller
public class UserController
{
	@Autowired
	private UserServices services;

	@PostMapping("/addingUser")
	public String  addUser(@ModelAttribute User user)
	{
		System.out.println(user);
		this.services.addUser(user);
		return "redirect:/order";
	}

	@GetMapping("/addingUser")
	


	@GetMapping("/updatingUser/{id}")
	public String updateUser(@ModelAttribute User user, @PathVariable("id") int id)
	{
		this.services.updateUser(user, id);
		return "redirect:/order";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id" )int id)
	{
		this.services.deleteUser(id);
		return "redirect:/admin/services";
	}
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());  // empty User object form ke liye
		return "register";  // register.html file render karega
	}


	// 1. Show the registration form
	

	


}

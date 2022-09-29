package com.greatlearning.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.crm.entity.Customer;
import com.greatlearning.crm.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@RequestMapping("/list")
	private String listCustomer(Model theModel) {
		List<Customer> theCustomer = customerService.findAll();
		theModel.addAttribute("Customer", theCustomer);
		return "list-Customer";
	}
	@RequestMapping("/showFormForAdd")
	private String showFormForAdd(Model theModel) {
		Customer theCustomer = new Customer();
		theModel.addAttribute("Customer", theCustomer);
		return "Customer-form";
	}
	@RequestMapping("/showFormForUpdate")
	private String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		Customer theCustomer = customerService.findById(theId);
		theModel.addAttribute("Customer", theCustomer);
		return "Customer-form";
	}
	@PostMapping("/save")
	public String saveCustomer(@RequestParam("id") int id, @RequestParam("firstname") String firstname, 
			@RequestParam("lastname") String lastname, @RequestParam("email") String email) {
		System.out.println(id);
		Customer theCustomer;
		if(id!=0) {
			theCustomer = customerService.findById(id);
			theCustomer.setFirstname(firstname);
			theCustomer.setLastname(lastname);
			theCustomer.setEmail(email);
		}
		else {
			theCustomer = new Customer(firstname, lastname, email);
			customerService.save(theCustomer);
			return "redirect:/customer/list";
		}
		return "redirect:/customer/list";
	}
	@RequestMapping("/delete")
	public String delete(@RequestParam("customerId") int theId) {
		customerService.deleteById(theId);
		return "redirect:/customer/list";
	}
}

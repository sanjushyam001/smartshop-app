package com.smartshop.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("")
	public String viewHomePage() {
		System.out.println("test");
		System.out.println("review changes done !");
		return "index";
	}
}

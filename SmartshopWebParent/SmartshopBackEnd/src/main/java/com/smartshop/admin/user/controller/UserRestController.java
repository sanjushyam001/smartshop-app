package com.smartshop.admin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.admin.user.service.UserService;

@RestController
public class UserRestController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/users/check_email")
	public boolean isDuplicateEmail(@Param("id") Integer id,@Param("email") String email) {
		
		return !userService.isEmailUnique(id,email);
	}
}

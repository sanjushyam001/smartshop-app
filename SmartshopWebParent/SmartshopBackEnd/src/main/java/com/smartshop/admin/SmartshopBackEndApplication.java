package com.smartshop.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.smartshop.common.entity","com.smartshop.admin.user"})
public class SmartshopBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartshopBackEndApplication.class, args);
	}

}

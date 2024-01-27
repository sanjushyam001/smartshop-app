package com.smartshop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

	@Test
	public void testEncoderPassword() {
		
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String rawPassword="sanju";
		String encodePassword =encoder.encode(rawPassword);
		System.out.println("Encoded Password: "+encodePassword);
		boolean result= encoder.matches(rawPassword, encodePassword);
		System.out.println("RESULT :"+result);
		assertThat(result).isTrue();
	}
}

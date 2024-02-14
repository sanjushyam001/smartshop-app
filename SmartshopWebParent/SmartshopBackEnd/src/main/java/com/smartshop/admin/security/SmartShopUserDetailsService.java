package com.smartshop.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smartshop.admin.user.repository.UserRepository;
import com.smartshop.common.entity.User;

public class SmartShopUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("Email: "+email);
		User user = userRepository.getUserByEmail(email);
		System.out.println("USER: "+user);
		if(user!=null) {
			System.out.println("USER NAME: "+user.getEmail());
			return new SmartShopUserDetails(user);
		}
		throw new UsernameNotFoundException("Could not find user with username: "+email);
	}

}

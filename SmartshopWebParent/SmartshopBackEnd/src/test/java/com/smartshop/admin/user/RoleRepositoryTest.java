package com.smartshop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.smartshop.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository repository;
	
	@Test
	public void testCreateFirstRole() {
		
		Role roleAdmin=new Role("Admin","Manage Everything");
		Role savedRole= repository.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}

}

package com.smartshop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.smartshop.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository repository;

	@Test
	public void testCreateFirstRole() {

		Role roleAdmin = new Role("Admin", "Manage Everything");
		Role savedRole = repository.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateRestRoles() {

		Role editor = new Role("editor", "editor");

		Role salesPerson = new Role("salesPerson", "salesPerson");
		Role assistant = new Role("assistant", "assistant");

		Role shipper = new Role("shipper", "shipper");

		repository.saveAll(List.of(editor,salesPerson,assistant,shipper));
	}

}

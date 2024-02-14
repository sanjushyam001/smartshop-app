package com.smartshop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.smartshop.admin.user.repository.UserRepository;
import com.smartshop.common.entity.Role;
import com.smartshop.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class UserRepositoryTest {


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		
		Role roleAdmin = entityManager.find(Role.class, 3);
		System.out.println("Role : "+roleAdmin);
		
		User firstUser = User.builder()
		.email("sitamaa@ayodhya.in")
		.firstName("Sita")
		.lastName("Maa")
		.password("JaiSitaMaa")
		.roles(new HashSet<>())
		.photos("sitamaaphotos.jpg")
		.enabled(true)
		.build();
		
		firstUser.addRole(roleAdmin);
		
		assertThat(userRepository.save(firstUser).getId()).isGreaterThan(0);
	}
	@Test
	public void testCreateUserWithTwoRoles() {
		
		Role roleAdmin = entityManager.find(Role.class, 3);
		Role roleEditor = entityManager.find(Role.class, 4);
		System.out.println("Role : "+roleAdmin);
		
		User secodUser = User.builder()
		.email("rajadasrath@ayodhya.in")
		.firstName("Raja")
		.lastName("Dashrath")
		.password("rajadasrath")
		.roles(new HashSet<>())
		.photos("photo.jpg")
		.enabled(true)
		.build();
		
		secodUser.addRole(roleAdmin);
		secodUser.addRole(roleEditor);
		
		assertThat(userRepository.save(secodUser).getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		
		userRepository.findAll().forEach(u->System.out.println(u));
	}
	@Test
	public void getUserById() {
		
		Optional<User> userOptional = userRepository.findById(1);
		assertThat(userOptional.get()).isNotNull();
	}
	
	@Test
	public void testGetUserByEmailTest() {
		User userByEmail = userRepository.getUserByEmail("shriram@ayodhya.in");
		assertThat(userByEmail).isNotNull();
	}
	@Test
	public void testCountById() {
		Integer id=1;
		Long countById = userRepository.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testUpdateEnableStatus() {
		
		Integer id=1;
		
		userRepository.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		
		int pageNumber=0;
		int pageSize=4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = userRepository.findAll(pageable);
		List<User> users = page.getContent();
		users.forEach(System.out::println);
		System.out.println(users);
		
		
	}
	@Test
	public void testSearchUser() {
		String keyword="Evran";
		
		Integer pageNumber=0;
		Integer pageSize=4;
		
		Pageable pageable =PageRequest.of(pageNumber, pageSize);
		Page<User> page= userRepository.findAll(keyword, pageable);
		List<User> listUsers= page.getContent();
		listUsers.forEach(user->System.out.println(user));
		assertThat(listUsers.size()).isGreaterThan(0);
	}

}

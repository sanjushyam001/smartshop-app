package com.smartshop.admin.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartshop.common.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

//	@Query("SELECT u FROM USER u WHERE u.email=:email")
//	public User getUserByEmail(@Param("email") String email);
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User getUserByEmail(String email);
	
	public Long countById(Integer id);
	
	@Query("UPDATE User u SET u.enabled=?2 WHERE u.id=?1")
	@Modifying
	public void updateEnabledStatus(Integer id,boolean enabled);

}

package com.smartshop.admin.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartshop.common.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}

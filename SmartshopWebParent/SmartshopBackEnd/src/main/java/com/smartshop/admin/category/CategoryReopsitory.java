package com.smartshop.admin.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartshop.common.entity.Category;

public interface CategoryReopsitory extends JpaRepository<Category, Integer> {

	@Query("SELECT c FROM Category c WHERE c.parentCategory.id is NULL")
	List<Category> findRootCategories();
}

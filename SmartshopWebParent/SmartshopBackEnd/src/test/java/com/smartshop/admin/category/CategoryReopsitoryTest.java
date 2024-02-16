package com.smartshop.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.smartshop.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryReopsitoryTest {

	@Autowired
	private CategoryReopsitory categoryReopsitory;
	
	
	@Test
	public void testCreateRootCategory() {
		Category category=new Category("Electronics");
		
		Category savedCategory = categoryReopsitory.save(category);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSubCategory() {
		Category parentCategory=new Category(1);
		Category memory=new Category("Hard Disk",parentCategory);
		Category ram=new Category("RAM",parentCategory);
		//Category cameras=new Category("cameras",parentCategory);
		
		//Category savedSubCategory = categoryReopsitory.save(subCategory);
		List<Category> saveAll = categoryReopsitory.saveAll(Arrays.asList(memory,ram));
		//assertThat(savedSubCategory.getId()).isGreaterThan(0);
		
		assertThat(saveAll.size()).isEqualTo(2);
	}
	
	@Test
	public void testGetCategory() {
		Category parentCategory=categoryReopsitory.findById(1).get();
		
		String categoryName = parentCategory.getName();
		
		System.out.println("CATEGORY NAME: "+categoryName);
		//assertThat(categoryName).isEqualTo("Computers");
		
		
		parentCategory.getChildrencategories().forEach(subcategory->{
			System.out.println(subcategory.getName());
		});
	}
	
	@Test
	public void testListRootCategories() {
		List<Category> listRootCategories = categoryReopsitory.findRootCategories();
		listRootCategories.forEach(rc->{
			System.out.println(rc.getName());
		});
	}
	
}

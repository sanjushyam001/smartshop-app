package com.smartshop.admin.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartshop.common.entity.Category;

@Service
public class CategoryService {

	@Autowired
	private CategoryReopsitory categoryReopsitory;
	
	public List<Category> listAllCategories(){
		
		List<Category> rootCategories = categoryReopsitory.findRootCategories();
		
		return listHierarchicalCategories(rootCategories);
	}
		public List<Category> listHierarchicalCategories(List<Category> rootCategories){
		
		List<Category> listHierarchicalCategories = new ArrayList<>();
		System.out.println("############ listHierarchicalCategories ###################");
		rootCategories.forEach(rc->{
			System.out.println(rc.getId()+" "+rc.getName());
			listHierarchicalCategories.add(rc);
			Set<Category> childrencategories = rc.getChildrencategories();
			childrencategories.forEach(childCategory->{
				System.out.println(childCategory.getId()+" "+childCategory.getName());
				listHierarchicalCategories.add(childCategory);
				
				Set<Category> subChildrencategories=childCategory.getChildrencategories();
				subChildrencategories.forEach(subChildCategory->{
					System.out.println(subChildCategory.getId()+" "+subChildCategory.getName());
					listHierarchicalCategories.add(subChildCategory);
				});
				
			});
		});
		
		return listHierarchicalCategories;
	}
	
	public List<Category> listAllCategoriesUseInForm(){
		//System.out.println("listAllCategoriesUseInForm() called");
		List<Category> categoriesUsedInForm=new ArrayList<>();
		Iterable<Category> categoriesInDB = categoryReopsitory.findAll();
		
		categoriesInDB.forEach(parentCategory->{
//			System.out.println("BEFOR NULL Parent "+parentCategory.getName());
			// parent category should be null 
			if(parentCategory.getParentCategory()==null) {
//				System.out.println("Parent "+parentCategory.getName());
				categoriesUsedInForm.add(Category.categoryIdAndName(parentCategory));
				
				Set<Category> childrencategories = parentCategory.getChildrencategories();
				
				
				childrencategories.forEach(c->{
					String subCategoryName="--"+c.getName();
					categoriesUsedInForm.add(Category.categoryIdAndName(c.getId(),subCategoryName));
					//System.out.println(subCategoryName);
					listChildren(categoriesUsedInForm,c,1);
				});
			}
		});
		
		return categoriesUsedInForm;
	}
	public  void listChildren(List<Category> categoriesUsedInForm,Category parent,int subLevel) {
		int newLevel=subLevel+1;
		Set<Category> childrencategories = parent.getChildrencategories();
		
		childrencategories.forEach(sc->{
			String name="";
			for(int i=0;i<newLevel;i++) {
				//System.out.print("--");
				name+="--";
			}
			name+=sc.getName();
			System.out.println(name);
			categoriesUsedInForm.add(Category.categoryIdAndName(sc.getId(),name));
			listChildren(categoriesUsedInForm,sc,newLevel);
		});
	}
	
	public Category save(Category category) {
		return categoryReopsitory.save(category);
	}
}

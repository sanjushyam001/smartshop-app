package com.smartshop.admin.category;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smartshop.admin.util.FileUploadUtil;
import com.smartshop.common.entity.Category;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/categories")
	public String listAll(Model model) {
		List<Category> categories=categoryService.listAllCategories();
		System.out.println("CATEGORIES: "+categories);
		model.addAttribute("categories",categories);
		
		for(Category parentCategory:categories ) {
			System.out.println(parentCategory.getName());
			for(Category childCategory:parentCategory.getChildrencategories()) {
				System.out.println(childCategory.getName());
			}
		}
		return "categories/categories";
	}
	
	@GetMapping("/categories/new")
	public String newCategory(Model model) {
		
		List<Category> listAllCategoriesUseInForm = categoryService.listAllCategoriesUseInForm();
		System.out.println("LIST CATEGORY = "+listAllCategoriesUseInForm);
		Category category=new Category();
		model.addAttribute("category",category);
		model.addAttribute("listCategories",listAllCategoriesUseInForm);
		model.addAttribute("pageTitle","Create New Category");
		return "categories/category_form";
	}
	@PostMapping("/categories/save")
	public String saveCategory(Category category,
			@RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) throws IOException {
		
		String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
		category.setImage(fileName);
		Category savedCategory = categoryService.save(category);
		String uploadDirectory="../category-images/"+savedCategory.getId();
		FileUploadUtil.saveFile(uploadDirectory, fileName, multipartFile);
		redirectAttributes.addFlashAttribute("message","The Category has been saved successfully");
		return "redirect:/categories";
	}
}

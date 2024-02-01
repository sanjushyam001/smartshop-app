package com.smartshop.admin.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smartshop.admin.FileUploadUtil;
import com.smartshop.common.entity.Role;
import com.smartshop.common.entity.User;

@Controller
//@ResponseBody
public class UserController {

	@Autowired
	private UserService userService;

//	@GetMapping("/users")
//	public String listAll(Model model) {
//		List<User> listUsers = userService.listAll();
//		model.addAttribute("listUsers", listUsers);
//		model.addAttribute("pageTitle", "Create New User");
//		return "users";
//	}
	/*
	 * public List<User> listAll(){ return userService.listAll(); }
	 */

	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = userService.listRoles();
		System.out.println("LIST ROLES >>>> " + listRoles);
		for (Role role : listRoles) {
			System.out.println(role.getDescription());
		}

		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New User ");
		return "user_form";
	}

	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
//			System.out.println("User"+user);
//			System.out.println("File Name: "+multipartFile.getOriginalFilename());
			if(!multipartFile.isEmpty()) {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				user.setPhotos(fileName);
				User savedUser= userService.save(user);
				String uploadDir="user-photos/"+savedUser.getId();
				FileUploadUtil.cleanDir(uploadDir);
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			}else {
				if(user.getPhotos().isEmpty()) {
					user.setPhotos(null);
					userService.save(user);
				}
			}
			boolean isUpdatingUser=false;
			if(user.getId()!=null) {
				isUpdatingUser=true;
			}
//		boolean isUpdatingUser = userService.save(user);
		if (isUpdatingUser)
			redirectAttributes.addFlashAttribute("message", "User has been updated successfully");
		else
			redirectAttributes.addFlashAttribute("message", "User has been saved successfully");
		System.out.println(user);
//		redirectAttributes.addFlashAttribute("message", "User has been updated successfully");
		return "redirect:/users";
	}

	@GetMapping("/users/edit/{id}")
	public String editUSer(@PathVariable Integer id, RedirectAttributes redirectAttributes, Model model) {
		try {
			User user = userService.get(id);
			List<Role> listRoles = userService.listRoles();
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Edit user with thi id " + id);
			model.addAttribute("listRoles", listRoles);
			return "user_form";
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/users";
		}

	}

	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable Integer id, RedirectAttributes redirectAttributes, Model model) {

		try {
			userService.delete(id);
			redirectAttributes.addFlashAttribute("message", "User with id " + id + "has been deleted successfully!");
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());

		}
		return "redirect:/users";
	}
	
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnableStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
			
		userService.updateUserEnableStatus(id, enabled);
		String status=enabled?"Enabled ":" Disabled";
		redirectAttributes.addFlashAttribute("message", "User with id " + id+ " "+status+" successfully!");

		return "redirect:/users";
	}
	@GetMapping("/users")
	public String listFirstPage(Model model) {
		return listByPage(1, model,"firstName","asc",null);
	}
	
	@GetMapping("/users/page/{pageNumber}")
	public String listByPage(@PathVariable Integer pageNumber,
			Model model,
			@Param("sortField") String sortField,
			@Param("sortDir")  String sortDir,
			@Param("keyword") String keyword) {
		
		Page<User> page = userService.listByPage(pageNumber,sortField,sortDir,keyword);
		System.out.println("Page Number : "+pageNumber);
		System.out.println("Total Elements : "+page.getTotalElements());
		System.out.println("Total Pages: "+page.getTotalPages());
		
		int startCount=(pageNumber-1)*userService.USERS_PER_PAGE+1;
		System.out.println("START COUNT: "+startCount);
		int endCount=startCount+userService.USERS_PER_PAGE-1;
		System.out.println("END COUNT: "+endCount);
		if(endCount>page.getTotalElements()) {
			endCount= (int) page.getTotalElements();
		}
		List<User> users = page.getContent();
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("listUsers", users);
		model.addAttribute("sortField", sortField);
		String reverseSortDir=sortDir.equals("asc")?"desc":"asc";
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
//		model.addAttribute("totalItems",0);
//		model.addAttribute("listUsers", new ArrayList<>());
		return "users";
	}
}

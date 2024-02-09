package com.smartshop.admin.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartshop.common.entity.Role;
import com.smartshop.common.entity.User;


/**
 * UserService class provides functionality related to user management. It
 * includes methods for saving the user, email uniqueness, and retrieving a list
 * of users and their roles.
 */
@Service
@Transactional
public class UserService {

	public static final int USERS_PER_PAGE=4;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Retrieves a list of all users .
	 *
	 * @return A list of User objects containing user information .
	 */
	public List<User> listAll() {
		return userRepository.findAll(Sort.by("firstName").ascending());
	}

	/**
	 * Retrieves a list of all user's roles .
	 *
	 * @return A list of User objects containing user information .
	 */
	public List<Role> listRoles() {
		return roleRepository.findAll();
	}

	/**
	 * Saving a new user .
	 *
	 * @param User object which contains all the information related to a user
	 *             chosen by the user.
	 * @return
	 */

//	public User save(User user) {
//		return userRepository.save(user);
//		
//	}
	public User save(User user) {
		boolean isUpdatingUser=false;
		if(user.getId()!=null) {
			isUpdatingUser=true;
		}
		if(isUpdatingUser) {
			User existingUser= get(user.getId());
			if(user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			}else {
				encodePassword(user);
			}
		}
		
		encodePassword(user);
		return userRepository.save(user);
		
	}

	/**
	 * The {@code isEmailUnique} method is responsible for handling user
	 * registration and ensuring that email addresses are unique.
	 *
	 * @param email The email address of the user
	 * @return If the provided email address is already registered
	 */

	/**
	 * Retrieve the user from the repository by using email address Check if the
	 * user exists with provided email address
	 * 
	 * @return false If the provided email address is already registered
	 */
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepository.getUserByEmail(email);
		if(userByEmail == null) return true;
		boolean isCreatingNew=id==null;
		if(isCreatingNew) {
			if(userByEmail!=null) {
				return false;
			}
		}else {
			if(userByEmail.getId()!=id) {
				return false;
			}
		}
		return true;
	}

	/**
     * Retrieves users by page from the database.
     *
     * @param page number 
     *@return A Page object containing the requested users.
     */
	
	public Page<User> listByPage(Integer pageNumber,String sortField,String sortDir,String keyword){
		
		Sort sort = Sort.by(sortField);
		sort=sortDir.equals("asc")?sort.ascending():sort.descending();
		
		Pageable pageable = PageRequest.of(pageNumber-1, USERS_PER_PAGE,sort);
		if(keyword!=null) {
			return userRepository.findAll(keyword,pageable);
		}
		return userRepository.findAll(pageable);
	}
	public void delete(Integer id) {
		
		Long countById=userRepository.countById(id);
		if(countById==null || countById==0) {
			throw new UserNotFoundException("Could not find the user with given id"+id);
		}
		userRepository.deleteById(id);
	}
	
	public void updateUserEnableStatus(Integer id,boolean enabled) {
		userRepository.updateEnabledStatus(id, enabled);
	}
	/**
	 * Constructs a new encodePassword method with the PasswordEncoder.
	 *
	 * @param User            Object repository for user data.
	 * @param passwordEncoder The encoder for password hashing.
	 */
	private void encodePassword(User user) {
		// extract user password from user object and the provided encode raw password
		// and update it
		// with encoded password
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	

	public User get(Integer id) {
		

		return userRepository.findById(id)
				.orElseThrow(()-> new UserNotFoundException("Could not find any user with this " + id));
	}
}

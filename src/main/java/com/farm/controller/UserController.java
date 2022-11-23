package com.farm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farm.model.Crop;
import com.farm.model.ResponseModel;
import com.farm.model.User;
import com.farm.repo.UserRepo;

@RestController
@RequestMapping("api/auth/")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping(value = "all")
	public List<User> all() {
		
		return userRepo.findAllUser();
	}
	
	@PostMapping(value = "register")
	public User register(@RequestBody User user) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		
		String hashedpassword = bcrypt.encode(user.getPassword());
		user.setPassword(hashedpassword);
		User result = userRepo.save(user);
		
		result.setPassword("");
		
		return result;
	}
	
	@PostMapping(value = "login")
	public ResponseEntity<ResponseModel> login(@RequestBody User user) {
		System.out.println(user.getUsername());
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		
		Optional<User> userData = Optional.ofNullable(userRepo.findUserName(user.getUsername()));
		
		if(userData.isPresent() && userData.get().getActive() && 
				bcrypt.matches(user.getPassword(), userData.get().getPassword()) && 
				user.getUsername().equals(userData.get().getUsername())) {
		return ResponseEntity.ok().body(new ResponseModel(1, "success", userData.get()));
		}
		if(userData.isPresent() && !userData.get().getActive()) {
			return ResponseEntity.ok().body(new ResponseModel(0, "your account has been deactivated", null));
		}
		return ResponseEntity.ok().body(new ResponseModel(0, "incorrect username or password", null));
	}
	
	@GetMapping(value = "details/{id}")
	private ResponseEntity<ResponseModel> details(@PathVariable Long id) {
		Optional<User> user = userRepo.findById(id);
		if(user.isEmpty()) {
			 return ResponseEntity.ok().body(new ResponseModel(0, "user not exist", null));
		}
		
		return ResponseEntity.ok().body(new ResponseModel(1, "exist", user.get()));
	}

}

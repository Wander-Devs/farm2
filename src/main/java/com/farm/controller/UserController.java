package com.farm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.farm.model.Crop;
import com.farm.model.ResponseModel;
import com.farm.model.User;
import com.farm.repo.UserRepo;

@RestController
@RequestMapping("api/auth/")
@CrossOrigin(origins = "*" )
public class UserController {
	
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping(value = "all")
	public List<User> all() {
		
		return userRepo.findAllUser();
	}
	
	@PatchMapping(value = "update/{user_id}")
	public ResponseEntity<ResponseModel> update(@RequestBody User user) {
		
		Optional<User> userData = userRepo.findById(user.getUser_id());
		if(userData.isEmpty()) {
			return ResponseEntity.ok().body( new ResponseModel(0, "User does not exist", null));
		}
		
		userRepo.save(user);
		return ResponseEntity.ok().body( new ResponseModel(1, "User has been updated", null));
		
	}
	
	@DeleteMapping(value = "delete/{user_id}")
	public ResponseEntity<ResponseModel> delete(@PathVariable(value = "user_id") Long user_id) {
		
		Optional<User> userData = userRepo.findById(user_id);
		if(userData.isEmpty()) {
			return ResponseEntity.ok().body( new ResponseModel(0, "User does not exist", null));
		}
		userRepo.deleteById(userData.get().getUser_id());
		return ResponseEntity.ok().body( new ResponseModel(1, "User has been deleted", null));
	}
	
	@PostMapping(value = "register")
	public ResponseEntity<ResponseModel> register(@RequestBody User user) {
		Optional<User> findUser = Optional.ofNullable(userRepo.findByUsername(user.getUsername()));
		if(findUser.isPresent()) {
			return ResponseEntity.ok().body( new ResponseModel(0, "User already exist", null));
		}else {
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			
			String hashedpassword = bcrypt.encode(user.getPassword());
			user.setPassword(hashedpassword);
			userRepo.save(user);
			//User result = userRepo.save(user);	
			//result.setPassword("");
			
			return ResponseEntity.ok().body( new ResponseModel(1, "success", null));
		}
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

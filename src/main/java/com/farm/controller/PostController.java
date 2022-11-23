package com.farm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farm.model.Post;
import com.farm.model.ResponseModel;
import com.farm.repo.PostRepo;

@RestController
@RequestMapping("api/post/")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
	
	@Autowired
	private PostRepo postRepo;
	
	@GetMapping(value = "all")
	public List<Post> all() {
		
		return postRepo.findAllPost();
	}
	
	@PostMapping(value = "add")
	public ResponseEntity<ResponseModel> addPost(@RequestBody Post post) {
		
		postRepo.save(post);
		return ResponseEntity.ok().body( new ResponseModel(1, "success", null));
		
	}
	
	@GetMapping(value = "userpost/{id}")
	public List<Post> findMyPost(@PathVariable Long id) {
		return postRepo.findPostByUserId(id);
	}
	
	@PatchMapping(value = "update")
	public ResponseEntity<ResponseModel> update(@RequestBody Post post) {
		
		Optional<Post> postData = postRepo.findById(post.getPost_id());
		if(postData.isEmpty()) {
			return ResponseEntity.ok().body( new ResponseModel(0, "post does not exist", null));
		}
		
		postRepo.save(post);
		return ResponseEntity.ok().body( new ResponseModel(1, "post has been modified", null));
		
	}

}

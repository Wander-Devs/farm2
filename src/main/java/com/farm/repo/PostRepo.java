package com.farm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.farm.model.Post;

public interface PostRepo extends JpaRepository<Post, Long>{
	
	@Query(nativeQuery = true, value = "select *from post_tbl where poster_user_id = ?1")
	List<Post> findPostByUserId(Long post_id);
	
	@Query(nativeQuery = true, value = "select *from post_tbl where deleted <> true")
	List<Post> findAllPost();

}

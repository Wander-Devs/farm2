package com.farm.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.farm.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
	
	@Transactional
	@Query(nativeQuery = true, value = "select *from user_tbl where username = ?1")
	User findUserName(String username);
	
	@Query(nativeQuery = true, value = "select *from user_tbl where active <> false")
	List<User> findAllUser();
}

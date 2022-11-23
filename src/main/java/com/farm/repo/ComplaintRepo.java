package com.farm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.farm.model.Complaint;



public interface ComplaintRepo extends JpaRepository<Complaint, Long>{

	@Query(nativeQuery = true, value = "select *from complaint_tbl where complainant_user_id_user_id = ?1")
	List<Complaint> findComplaintByUserId(Long user_id);
	
	@Query(nativeQuery = true, value = "select *from complaint_tbl where deleted <> true")
	List<Complaint> findAllComplaint();
}

package com.farm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.farm.model.Crop;

public interface CropRepo extends JpaRepository<Crop, Long> {
	
	@Query(nativeQuery = true, value = "select *from crop where crop_owner_user_id = ?1")
	List<Crop> findCropByUserId(Long user_id);
	
	@Query(nativeQuery = true, value = "select *from crop where deleted <> true")
	List<Crop> findAllCrop();

}

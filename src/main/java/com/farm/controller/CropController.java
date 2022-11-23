package com.farm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.farm.model.Crop;
import com.farm.model.ResponseModel;
import com.farm.repo.CropRepo;

@RestController
@RequestMapping("api/crop/")
@CrossOrigin(origins = "http://localhost:3000")
public class CropController {
	
	@Autowired
	private CropRepo cropRepo;
	
	@GetMapping(value = "all")
	public List<Crop> all() {
		
		return cropRepo.findAllCrop();
	}
	
	@PostMapping(value = "add")
	public ResponseEntity<ResponseModel> addCrop(@RequestBody Crop crop) {
		
		cropRepo.save(crop);
		return ResponseEntity.ok().body( new ResponseModel(1, "success", null));
		
	}
	
	@GetMapping(value = "usercrop/{id}")
	public List<Crop> findMyCrop(@PathVariable Long id) {
		return cropRepo.findCropByUserId(id);
	}
	
	@PatchMapping(value = "update")
	public ResponseEntity<ResponseModel> update(@RequestBody Crop crop) {
		
		Optional<Crop> cropData = cropRepo.findById(crop.getCrop_id());
		if(cropData.isEmpty()) {
			return ResponseEntity.ok().body( new ResponseModel(0, "crop does not exist", null));
		}
		
		cropRepo.save(crop);
		return ResponseEntity.ok().body( new ResponseModel(1, "crop has been modified", null));
		
	}
	
	@DeleteMapping(value = "delete")
	public ResponseEntity<ResponseModel> delete(@RequestParam Long crop_id) {
		Optional<Crop> cropData = cropRepo.findById(crop_id);
		if(cropData.isEmpty()) {
			return ResponseEntity.ok().body( new ResponseModel(0, "crop does not exist", null));
		}
		
		cropData.get().setDeleted(true);
		cropRepo.save(cropData.get());
		return ResponseEntity.ok().body( new ResponseModel(1, "crop has been deleted", null));
	}

}

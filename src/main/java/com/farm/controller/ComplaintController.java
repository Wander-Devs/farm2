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

import com.farm.model.Complaint;
import com.farm.model.ResponseModel;
import com.farm.repo.ComplaintRepo;


@RestController
@RequestMapping("api/complaint/")
@CrossOrigin(origins = "http://localhost:3000")
public class ComplaintController {
	
	@Autowired
	private ComplaintRepo complaintRepo;
	
	@GetMapping(value = "all")
	public List<Complaint> all() {
		
		return complaintRepo.findAllComplaint();
	}
	
	@PostMapping(value = "add")
	public ResponseEntity<ResponseModel> addComplaint(@RequestBody Complaint complaint) {
		
		complaintRepo.save(complaint);
		return ResponseEntity.ok().body( new ResponseModel(1, "success", null));
		
	}
	
	@GetMapping(value = "usercomplaint/{id}")
	public List<Complaint> findMyComplaint(@PathVariable Long id) {
		return complaintRepo.findComplaintByUserId(id);
	}
	
	@PatchMapping(value = "update")
	public ResponseEntity<ResponseModel> update(@RequestBody Complaint complaint) {
		
		Optional<Complaint> complaintData = complaintRepo.findById(complaint.getComplaint_id());
		if(complaintData.isEmpty()) {
			return ResponseEntity.ok().body( new ResponseModel(0, "complaint does not exist", null));
		}
		
		complaintRepo.save(complaint);
		return ResponseEntity.ok().body( new ResponseModel(1, "complaint has been modified", null));
		
	}

}

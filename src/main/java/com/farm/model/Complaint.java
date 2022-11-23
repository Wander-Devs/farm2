package com.farm.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "complaint_tbl")
public class Complaint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long complaint_id;
	
	private String complain_title;
	
	private String complain_summary;
	
	private LocalDate createdDate = LocalDate.now();
	
	private Boolean isSolved = false;
	
	private Boolean deleted = false;
	
	@ManyToOne
	private User complainant_user_id;

	public Long getComplaint_id() {
		return complaint_id;
	}

	public void setComplaint_id(Long complaint_id) {
		this.complaint_id = complaint_id;
	}

	public String getComplain_title() {
		return complain_title;
	}

	public void setComplain_title(String complain_title) {
		this.complain_title = complain_title;
	}

	public String getComplain_summary() {
		return complain_summary;
	}

	public void setComplain_summary(String complain_summary) {
		this.complain_summary = complain_summary;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getIsSolved() {
		return isSolved;
	}

	public void setIsSolved(Boolean isSolved) {
		this.isSolved = isSolved;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public User getComplainant_user_id() {
		return complainant_user_id;
	}

	public void setComplainant_user_id(User complainant_user_id) {
		this.complainant_user_id = complainant_user_id;
	}

	
	
}
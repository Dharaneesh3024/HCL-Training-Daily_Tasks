package com.carrental.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.model.Branch;
import com.carrental.service.BranchService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/branches")
public class BranchController {
	private final BranchService branchService;
	public BranchController(BranchService branchService) {
			this.branchService=branchService;
	}
	
	@PostMapping
	public ResponseEntity<Branch> addBranch(@Valid @RequestBody Branch branch){
		Branch newBranch=branchService.addBranch(branch);
		return ResponseEntity.status(HttpStatus.CREATED).body(newBranch);
	}
	
	@GetMapping
	public ResponseEntity<List<Branch>> getAllBranches(){
		return ResponseEntity.ok(branchService.getAllBranches());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Branch> getBranchById(@PathVariable int id){
		Branch requestedBranch=branchService.getBranchById(id);
		return ResponseEntity.ok(requestedBranch);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Branch> updateBranch(@PathVariable int id,@Valid @RequestBody Branch branch){
		return ResponseEntity.ok(branchService.updateBranch(id, branch));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBranch(@PathVariable int id){
		branchService.deleteBranch(id);
		return ResponseEntity.noContent().build();
	}
}

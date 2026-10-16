
package com.carrental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.carrental.model.Branch;
import com.carrental.repository.BranchRepository;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch addBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Branch getBranchById(int id) {
        return branchRepository.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException("Branch not found with id: " + id));
    }

    public Branch updateBranch(int id, Branch updatedBranch) {
        Branch existingBranch = getBranchById(id);

        existingBranch.setName(updatedBranch.getName());
        existingBranch.setAddress(updatedBranch.getAddress());
        existingBranch.setCity(updatedBranch.getCity());
        existingBranch.setPhone(updatedBranch.getPhone());

        return branchRepository.save(existingBranch);
    }
    
  

    public void deleteBranch(int id) {
        Branch branch = getBranchById(id);
        branchRepository.delete(branch);
    }
}

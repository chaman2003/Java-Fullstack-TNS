package com.tns.placementmanagment.services.impl;

import com.tns.placementmanagment.entities.College;
import com.tns.placementmanagment.repositories.CollegeRepository;
import com.tns.placementmanagment.services.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService {

    @Autowired
    public CollegeRepository collegeRepository;

    private Long findNextAvailableId() {
        List<Long> existingIds = collegeRepository.findAllIds();
        long nextId = 1;
        for (Long id : existingIds) {
            if (id != nextId) {
                return nextId;
            }
            nextId++;
        }
        return nextId;
    }

    @Override
    public College createCollege(College college) {
        college.setId(findNextAvailableId());
        return collegeRepository.save(college);
    }

    @Override
    public List<College> getCollege() {
        return (List<College>) collegeRepository.findAll();
    }

    @Override
    public College updateCollege(College college) {
        // Check if the college exists before updating
        if (college.getId() != null && collegeRepository.existsById(college.getId())) {
            return collegeRepository.save(college);
        }
        return null;
    }

    @Override
    public void deleteCollege(Long id) {
        collegeRepository.deleteById(id);
    }
}

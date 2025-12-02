package com.tns.placementmanagment.controllers;

import com.tns.placementmanagment.entities.College;
import com.tns.placementmanagment.services.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/college")
@CrossOrigin(origins = "*")
public class CollegeController {

    /**
     * College REST controller (concise flow):
     * - Receives HTTP requests for /api/college and delegates to CollegeService
     */

    @Autowired
    public CollegeService collegeService;

    @GetMapping
    public List<College> getColleges() {
        return collegeService.getCollege();
    }

    @PostMapping
    public College createCollege(@RequestBody College college) {
        return collegeService.createCollege(college);
    }

    @PutMapping
    public College updateCollege(@RequestBody College college){
        College updated = collegeService.updateCollege(college);
        if (updated == null) {
            throw new IllegalArgumentException("College not found with ID: " + college.getId());
        }
        return updated;
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCollege(@PathVariable(name = "id") Long id) {
        collegeService.deleteCollege(id);
    }

}

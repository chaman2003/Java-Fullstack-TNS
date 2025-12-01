package com.tns.placementmanagment.services;

import com.tns.placementmanagment.entities.College;

import java.util.List;

public interface CollegeService {
    College createCollege(College college);
    List<College> getCollege();
    College updateCollege(College college);
    void deleteCollege(Long id);
}

package com.tns.placementmanagment.services.impl;

import com.tns.placementmanagment.entities.Placement;
import com.tns.placementmanagment.repositories.PlacementRepository;
import com.tns.placementmanagment.services.PlacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacementServiceImpl implements PlacementService {

    @Autowired
    public PlacementRepository placementRepository;

    // Finds the first missing positive ID (1,2,3...) by scanning ordered IDs.
    // This enforces gap-free sequential IDs so deleted slots get reused.
    private Long findNextAvailableId() {
        List<Long> existingIds = placementRepository.findAllIds();
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
    public Placement createPlacement(Placement placement) {
        placement.setId(findNextAvailableId());
        return placementRepository.save(placement);
    }

    @Override
    public List<Placement> getPlacement() {
        return (List<Placement>) placementRepository.findAll();
    }

    @Override
    public Placement updatePlacement(Placement placement) {
        // Check if the placement exists before updating
        if (placement.getId() != null && placementRepository.existsById(placement.getId())) {
            return placementRepository.save(placement);
        }
        return null;
    }

    @Override
    public void deletePlacement(Long id) {
        placementRepository.deleteById(id);
    }
}

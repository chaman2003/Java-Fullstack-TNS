package com.tns.placementmanagment.controllers;

import com.tns.placementmanagment.entities.Placement;
import com.tns.placementmanagment.services.PlacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/placement")
@CrossOrigin(origins = "*")
public class PlacementController {

    /**
     * Placement REST controller (concise flow):
     * - Receives HTTP requests for /api/placement and delegates to PlacementService
     */

    @Autowired
    public PlacementService placementService;

    @GetMapping
    public List<Placement> getPlacements() {
        return placementService.getPlacement();
    }

    @PostMapping
    public Placement createPlacement(@RequestBody Placement placement) {
        return placementService.createPlacement(placement);
    }

    @PutMapping
    public Placement updatePlacement(@RequestBody Placement placement){
        Placement updated = placementService.updatePlacement(placement);
        if (updated == null) {
            throw new IllegalArgumentException("Placement not found with ID: " + placement.getId());
        }
        return updated;
    }

    @DeleteMapping(path = "/{id}")
    public void deletePlacement(@PathVariable(name = "id") Long id) {
        placementService.deletePlacement(id);
    }

}

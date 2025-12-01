package com.tns.placementmanagment.services;

import com.tns.placementmanagment.entities.Placement;

import java.util.List;

public interface PlacementService {
    Placement createPlacement(Placement placement);
    List<Placement> getPlacement();
    Placement updatePlacement(Placement placement);
    void deletePlacement(Long id);
}

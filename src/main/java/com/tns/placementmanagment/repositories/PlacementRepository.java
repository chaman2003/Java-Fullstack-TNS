package com.tns.placementmanagment.repositories;

import com.tns.placementmanagment.entities.Placement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlacementRepository extends JpaRepository<Placement, Long> {
    @Query("SELECT p.id FROM Placement p ORDER BY p.id")
    List<Long> findAllIds();
}

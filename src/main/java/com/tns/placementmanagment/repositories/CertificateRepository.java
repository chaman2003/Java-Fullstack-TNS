package com.tns.placementmanagment.repositories;

import com.tns.placementmanagment.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    @Query("SELECT c.id FROM Certificate c ORDER BY c.id")
    List<Long> findAllIds();
}

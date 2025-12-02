package com.tns.placementmanagment.services.impl;

import com.tns.placementmanagment.entities.Certificate;
import com.tns.placementmanagment.repositories.CertificateRepository;
import com.tns.placementmanagment.services.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    public CertificateRepository certificateRepository;

    // Finds the first missing positive ID (1,2,3...) by scanning ordered IDs.
    // This enforces gap-free sequential IDs so deleted slots get reused.
    private Long findNextAvailableId() {
        List<Long> existingIds = certificateRepository.findAllIds();
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
    public Certificate createCertificate(Certificate certificate) {
        certificate.setId(findNextAvailableId());
        return certificateRepository.save(certificate);
    }

    @Override
    public List<Certificate> getCertificate() {
        return (List<Certificate>) certificateRepository.findAll();
    }

    @Override
    public Certificate updateCertificate(Certificate certificate) {
        // Check if the certificate exists before updating
        if (certificate.getId() != null && certificateRepository.existsById(certificate.getId())) {
            return certificateRepository.save(certificate);
        }
        return null;
    }

    @Override
    public void deleteCertificate(Long id) {
        certificateRepository.deleteById(id);
    }
}

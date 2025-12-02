package com.tns.placementmanagment.services;

import com.tns.placementmanagment.entities.Certificate;

import java.util.List;

// Service interface

public interface CertificateService {
    Certificate createCertificate(Certificate certificate);
    List<Certificate> getCertificate();
    Certificate updateCertificate(Certificate certificate);
    void deleteCertificate(Long id);
}

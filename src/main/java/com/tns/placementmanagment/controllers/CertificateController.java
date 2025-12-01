package com.tns.placementmanagment.controllers;

import com.tns.placementmanagment.entities.Certificate;
import com.tns.placementmanagment.services.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/certificate")
@CrossOrigin(origins = "*")
public class CertificateController {

    @Autowired
    public CertificateService certificateService;

    @GetMapping
    public List<Certificate> getCertificates() {
        return certificateService.getCertificate();
    }

    @PostMapping
    public Certificate createCertificate(@RequestBody Certificate certificate) {
        return certificateService.createCertificate(certificate);
    }

    @PutMapping
    public Certificate updateCertificate(@RequestBody Certificate certificate){
        Certificate updated = certificateService.updateCertificate(certificate);
        if (updated == null) {
            throw new IllegalArgumentException("Certificate not found with ID: " + certificate.getId());
        }
        return updated;
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCertificate(@PathVariable(name = "id") Long id) {
        certificateService.deleteCertificate(id);
    }

}

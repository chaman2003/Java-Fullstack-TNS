package com.tns.placementmanagment.entities;

import jakarta.persistence.*;

/**
 * Certificate entity.
 * Flow (concise): Frontend -> Controller -> Service -> Repository -> PostgreSQL
 * - create: Service assigns a gap-free ID using repository.findAllIds()
 * - update: Service validates existence with existsById(id) before saving
 * - fields: id, year, college, qualification
 */
@Entity
@Table(name = "certificate")
public class Certificate {
    @Id
    private Long id;
    private int year;
    private String college;
    private String qualification;

    public Certificate() {}

    public Certificate(Long id, int year, String college, String qualification) {
        this.id = id;
        this.year = year;
        this.college = college;
        this.qualification = qualification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", year=" + year +
                ", college='" + college + '\'' +
                ", qualification='" + qualification + '\'' +
                '}';
    }
}

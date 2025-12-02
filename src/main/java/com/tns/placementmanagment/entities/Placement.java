package com.tns.placementmanagment.entities;

import jakarta.persistence.*;

/**
 * Placement entity.
 * Flow (concise): Frontend -> Controller -> Service -> Repository -> PostgreSQL
 */
@Entity
@Table(name = "placement")
public class Placement {
    @Id
    private Long id;
    private String name;
    private String qualification;
    private int year;

    public Placement() {}

    public Placement(Long id, String name, String qualification, int year) {
        this.id = id;
        this.name = name;
        this.qualification = qualification;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Placement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qualification='" + qualification + '\'' +
                ", year=" + year +
                '}';
    }
}

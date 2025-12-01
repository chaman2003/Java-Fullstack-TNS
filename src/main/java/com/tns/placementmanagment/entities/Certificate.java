package com.tns.placementmanagment.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "certificate")
public class Certificate {
    @Id
    private Long id;
    private int year;
    private String college;

    public Certificate() {}

    public Certificate(Long id, int year, String college) {
        this.id = id;
        this.year = year;
        this.college = college;
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

    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", year=" + year +
                ", college='" + college + '\'' +
                '}';
    }
}

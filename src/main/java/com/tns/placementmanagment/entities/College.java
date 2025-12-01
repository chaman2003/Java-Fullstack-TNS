package com.tns.placementmanagment.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "college")
public class College {
    @Id
    private Long id;
    private String collegeName;
    private String location;

    public College() {}

    public College(Long id, String collegeName, String location) {
        this.id = id;
        this.collegeName = collegeName;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "College{" +
                "id=" + id +
                ", collegeName='" + collegeName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

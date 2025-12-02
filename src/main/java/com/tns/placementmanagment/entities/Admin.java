package com.tns.placementmanagment.entities;

import jakarta.persistence.*;

/**
 * Admin entity.
 * Flow (concise): Frontend -> Controller -> Service -> Repository -> PostgreSQL
 * - create: Service assigns a gap-free ID using repository.findAllIds()
 * - update: Service validates existence with existsById(id) before saving
 */
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    private Long id;
    
    private String name;
    
    private String password;

    public Admin() {}

    public Admin(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

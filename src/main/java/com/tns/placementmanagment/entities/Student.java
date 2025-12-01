package com.tns.placementmanagment.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "student")
public class Student {
    @Id
    private Integer sid;
    private String name;
    private long phoneNo;
    private String collegeName;

    public Student() {}

    public Student(Integer sid, String name, long phoneNo, String collegeName) {
        this.sid = sid;
        this.name = name;
        this.phoneNo = phoneNo;
        this.collegeName = collegeName;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", name='" + name + '\'' +
                ", phoneNo=" + phoneNo +
                ", collegeName='" + collegeName + '\'' +
                '}';
    }
}

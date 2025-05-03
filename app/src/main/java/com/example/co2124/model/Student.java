package com.example.co2124.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Student {
    @PrimaryKey(autoGenerate = true)
    public int studentId;

    public String name;
    public String email;
    public String matricNumber;

    public Student(String name, String email, String matricNumber) {
        this.name = name;
        this.email = email;
        this.matricNumber = matricNumber;
    }
}

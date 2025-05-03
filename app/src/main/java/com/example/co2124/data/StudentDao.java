package com.example.co2124.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.co2124.model.Student;
import com.example.co2124.model.StudentWithCourses;

@Dao
public interface StudentDao {
    @Insert
    long insert(Student student);

    //lookup by matric number
    @Query("SELECT * FROM Student WHERE matricNumber = :matricNumber LIMIT 1")
    Student getByMatricNumber(String matricNumber);

    //fetch student with enrolled courses
    @Transaction
    @Query("SELECT * FROM Student WHERE studentId = :studentId")
    LiveData<StudentWithCourses> getStudentWithCourses(int studentId);

    //lookup by id
    @Query("SELECT * FROM Student WHERE studentId = :id")
    LiveData<Student> getById(int id);

    //update student record
    @Update void update(Student student);
}


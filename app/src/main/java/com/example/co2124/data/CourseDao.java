package com.example.co2124.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.co2124.model.Course;
import com.example.co2124.model.CourseStudentCrossRef;
import com.example.co2124.model.CourseWithStudents;

import java.util.List;

@Dao
public interface CourseDao {
    //insert or update course
    @Insert
    long insert(Course course);

    //link student to course
    @Insert
    void addStudentToCourse(CourseStudentCrossRef crossRef);

    //fetch all courses
    @Query("SELECT * FROM Course")
    LiveData<List<Course>> getAllCourses();

    //lookup by unique code
    @Query("SELECT * FROM Course WHERE courseCode = :code LIMIT 1")
    Course getByCourseCode(String code);

    //remove all enrollments for a course
    @Query("DELETE FROM CourseStudentCrossRef WHERE courseId = :courseId")
    void deleteEnrollmentsForCourse(int courseId);

    //delete course
    @Query("DELETE FROM Course WHERE courseId = :courseId")
    void deleteCourseById(int courseId);

    //count specific enrollment
    @Query("SELECT COUNT(*) FROM CourseStudentCrossRef WHERE courseId = :courseId AND studentId = :studentId")
    int countEnrollment(int courseId, int studentId);

    //unenroll one student
    @Query("DELETE FROM CourseStudentCrossRef WHERE courseId = :courseId AND studentId = :studentId")
    void removeStudentFromCourse(int courseId, int studentId);

    //fetch course with students
    @Transaction
    @Query("SELECT * FROM Course WHERE courseId = :courseId")
    LiveData<CourseWithStudents> getCourseWithStudents(int courseId);

    //helper to delete course and its enrollments
    @Transaction
    default void deleteCourseWithEnrollments(int courseId) {
        deleteEnrollmentsForCourse(courseId);
        deleteCourseById(courseId);
    }
}


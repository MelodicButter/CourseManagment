package com.example.co2124.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.co2124.model.CourseWithStudents;
import com.example.co2124.repo.CourseRepository;

public class CourseDetailsViewModel extends AndroidViewModel {
    private final CourseRepository repo;

    public CourseDetailsViewModel(@NonNull Application application) {
        super(application);
        repo = new CourseRepository(application);
    }

    //get course with students
    public LiveData<CourseWithStudents> getCourseWithStudents(int courseId) {
        return repo.getCourseWithStudents(courseId);
    }

    //remove student from course
    public void removeStudentFromCourse(int courseId, int studentId) {
        repo.removeStudentFromCourse(courseId, studentId);
    }
}

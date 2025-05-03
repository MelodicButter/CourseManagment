// app/src/main/java/com/example/co2124/viewmodel/CreateCourseViewModel.java
package com.example.co2124.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.co2124.model.Course;
import com.example.co2124.repo.CourseRepository;

public class CreateCourseViewModel extends AndroidViewModel {
    private final CourseRepository repository;

    public CreateCourseViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseRepository(application);
    }

    public LiveData<Boolean> createCourse(String courseCode,
                                          String courseName,
                                          String lecturerName) {
        Course course = new Course(courseCode, courseName, lecturerName);
        return repository.createCourse(course);
    }
}

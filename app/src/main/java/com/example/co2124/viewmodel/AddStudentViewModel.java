package com.example.co2124.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.co2124.repo.CourseRepository;

public class AddStudentViewModel extends AndroidViewModel {
    private final CourseRepository repo;

    public AddStudentViewModel(@NonNull Application app) {
        super(app);
        repo = new CourseRepository(app);
    }

    public LiveData<Boolean> enroll(int courseId,
                                    String name,
                                    String email,
                                    String matricNumber) {
        return repo.addStudentToCourse(courseId, name, email, matricNumber);
    }
}

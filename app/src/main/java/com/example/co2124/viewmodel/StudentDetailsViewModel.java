package com.example.co2124.viewmodel;


import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.co2124.data.AppDatabase;
import com.example.co2124.model.StudentWithCourses;

public class StudentDetailsViewModel extends AndroidViewModel {
    public StudentDetailsViewModel(@NonNull Application app) {
        super(app);
    }

    public LiveData<StudentWithCourses> getStudentWithCourses(int studentId) {
        return AppDatabase.getInstance(getApplication())
                .studentDao()
                .getStudentWithCourses(studentId);
    }
}
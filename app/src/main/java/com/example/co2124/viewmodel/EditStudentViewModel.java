package com.example.co2124.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.co2124.data.AppDatabase;
import com.example.co2124.data.StudentDao;
import com.example.co2124.model.Student;

public class EditStudentViewModel extends AndroidViewModel {
    private final StudentDao studentDao;

    public EditStudentViewModel(@NonNull Application app) {
        super(app);
        studentDao = AppDatabase.getInstance(app).studentDao();
    }

    public LiveData<Student> getStudent(int studentId) {
        return studentDao.getById(studentId);
    }

    public void updateStudent(Student s) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            studentDao.update(s);
        });
    }
}

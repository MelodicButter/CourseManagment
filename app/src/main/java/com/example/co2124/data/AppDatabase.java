package com.example.co2124.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.co2124.model.Course;
import com.example.co2124.model.CourseStudentCrossRef;
import com.example.co2124.model.Student;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Course.class, Student.class, CourseStudentCrossRef.class},
        version = 5,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract CourseDao courseDao();

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "course_database"
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public  abstract StudentDao studentDao();
}

package com.example.co2124.repo;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.co2124.data.AppDatabase;
import com.example.co2124.data.CourseDao;
import com.example.co2124.data.StudentDao;
import com.example.co2124.model.Course;
import com.example.co2124.model.CourseStudentCrossRef;
import com.example.co2124.model.CourseWithStudents;
import com.example.co2124.model.Student;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CourseRepository {
    //dao instances and executor
    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final LiveData<List<Course>> allCourses;
    private final ExecutorService io;

    //initialize daos and live data
    public CourseRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        courseDao    = db.courseDao();
        studentDao   = db.studentDao();
        allCourses   = courseDao.getAllCourses();
        io           = AppDatabase.databaseWriteExecutor;
    }

    //create course with unique code
    public LiveData<Boolean> createCourse(Course c) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        io.execute(() -> {
            Course existing = courseDao.getByCourseCode(c.courseCode);
            if(existing!=null) {
                result.postValue(false);
                return;
            }
            courseDao.insert(c);
            result.postValue(true);
        });
        return result;
    }

    //add or enroll student to course
    public LiveData<Boolean> addStudentToCourse(int courseId,
                                                String name,
                                                String email,
                                                String matricNumber) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        io.execute(() -> {
            Student s = studentDao.getByMatricNumber(matricNumber);
            long sid = (s==null)
                    ? studentDao.insert(new Student(name,email,matricNumber))
                    : s.studentId;

            if(courseDao.countEnrollment(courseId,(int)sid)>0) {
                result.postValue(false);
                return;
            }
            try {
                courseDao.addStudentToCourse(
                        new CourseStudentCrossRef(courseId,(int)sid)
                );
                result.postValue(true);
            } catch(SQLiteConstraintException e) {
                result.postValue(false);
            }
        });
        return result;
    }

    //unenroll student from course
    public void removeStudentFromCourse(int courseId,int studentId) {
        io.execute(() -> {
            courseDao.removeStudentFromCourse(courseId,studentId);
            Log.d("Repo","Removed student "+studentId+" from course "+courseId);
        });
    }

    //fetch course with its students
    public LiveData<CourseWithStudents> getCourseWithStudents(int courseId) {
        return courseDao.getCourseWithStudents(courseId);
    }

    //get all courses
    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    //insert a course
    public void insert(Course course) {
        io.execute(() -> courseDao.insert(course));
    }

    //delete course and enrollments
    public void deleteCourse(int courseId) {
        io.execute(() -> courseDao.deleteCourseWithEnrollments(courseId));
    }
}

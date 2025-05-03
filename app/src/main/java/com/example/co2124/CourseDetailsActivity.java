package com.example.co2124;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.co2124.adapter.StudentAdapter;
import com.example.co2124.model.CourseWithStudents;
import com.example.co2124.model.Student;
import com.example.co2124.viewmodel.CourseDetailsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;

public class CourseDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_COURSE_ID = "courseId";
    private int courseId;
    private CourseDetailsViewModel viewModel;
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);

        TextView tvCode     = findViewById(R.id.tvDetailCode);
        TextView tvName     = findViewById(R.id.tvDetailName);
        TextView tvLecturer = findViewById(R.id.tvDetailLecturer);
        RecyclerView rv     = findViewById(R.id.rvStudents);

        // setup adapter
        studentAdapter = new StudentAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(studentAdapter);

        //Short‐press to StudentDetailsActivity
        studentAdapter.setOnStudentClickListener(student -> {
            Intent i = new Intent(this, StudentDetailsActivity.class);
            i.putExtra("studentId", student.studentId);
            startActivity(i);
        });

        //Long‐press to Edit/Remove dialog
        studentAdapter.setOnStudentLongClickListener(student -> {
            new AlertDialog.Builder(this)
                    .setTitle(student.name)
                    .setItems(new CharSequence[]{"Edit", "Remove"}, (dlg, which) -> {
                        if (which == 0) {
                            // Edit
                            Intent i = new Intent(this, EditStudentActivity.class);
                            i.putExtra("studentId", student.studentId);
                            i.putExtra("courseId",  courseId);
                            startActivity(i);
                        } else {
                            // Remove enrollment only
                            viewModel.removeStudentFromCourse(courseId, student.studentId);
                            Toast.makeText(this,
                                    student.name + " removed from this course",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        });

        viewModel = new ViewModelProvider(this)
                .get(CourseDetailsViewModel.class);
        viewModel.getCourseWithStudents(courseId)
                .observe(this, cws -> {
                    if (cws == null) return;
                    tvCode.setText("Course Code: " + cws.course.courseCode);
                    tvName.setText("Course Name: " + cws.course.courseName);
                    tvLecturer.setText("Lecturer's Name: " + cws.course.lecturerName);
                    studentAdapter.setStudents(
                            cws.students != null
                                    ? cws.students
                                    : Collections.emptyList()
                    );
                });

        //FAB for adding students
        FloatingActionButton fab = findViewById(R.id.fabAddStudent);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddStudentActivity.class);
            intent.putExtra(EXTRA_COURSE_ID, courseId);
            startActivity(intent);
        });
    }
}

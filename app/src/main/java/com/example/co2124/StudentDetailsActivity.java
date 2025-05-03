package com.example.co2124;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.co2124.adapter.CourseListAdapter;
import com.example.co2124.model.StudentWithCourses;
import com.example.co2124.viewmodel.StudentDetailsViewModel;

public class StudentDetailsActivity extends AppCompatActivity {
    private StudentDetailsViewModel viewModel;
    private CourseListAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        TextView tvName   = findViewById(R.id.textStudentName);
        TextView tvEmail  = findViewById(R.id.textStudentEmail);
        TextView tvMatric = findViewById(R.id.textStudentMatric);
        RecyclerView rv   = findViewById(R.id.recyclerCourses);

        courseAdapter = new CourseListAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(courseAdapter);

        int studentId = getIntent().getIntExtra("studentId", -1);
        if (studentId == -1) return;  // or finish()

        viewModel = new ViewModelProvider(this)
                .get(StudentDetailsViewModel.class);

        viewModel.getStudentWithCourses(studentId)
                .observe(this, swc -> {
                    if (swc == null) return;
                    tvName.setText("Name: " + swc.student.name);
                    tvEmail.setText("Email: " + swc.student.email);
                    tvMatric.setText("Matric No: " + swc.student.matricNumber);
                    courseAdapter.setCourses(swc.courses);
                });
    }
}

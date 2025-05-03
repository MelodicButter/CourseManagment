package com.example.co2124;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.co2124.adapter.CourseAdapter;
import com.example.co2124.model.Course;
import com.example.co2124.viewmodel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private CourseViewModel courseViewModel;
    private CourseAdapter adapter;
    private TextView tvEmpty;

    //private boolean seeded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCourses);
        tvEmpty = findViewById(R.id.tvEmpty);

        adapter = new CourseAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this)
                .get(CourseViewModel.class);

        courseViewModel.getAllCourses().observe(this, courses -> {
            /*if (!seeded && (courses == null || courses.isEmpty())) {
                seeded = true;
                //seedCourses();
                return;
            }*/

            adapter.setCourses(courses);
            if (courses == null || courses.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        adapter.setOnItemClickListener(course -> {
            Intent i = new Intent(MainActivity.this, CourseDetailsActivity.class);
            i.putExtra(CourseDetailsActivity.EXTRA_COURSE_ID, course.courseId);
            startActivity(i);
        });

        adapter.setOnItemLongClickListener(course -> {
            new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete Course")
                    .setMessage("Are you sure you want to delete \""
                            + course.courseCode + " - "
                            + course.courseName + "\"?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        // User confirmed: delete the course
                        courseViewModel.deleteCourse(course.courseId);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        // User cancelled: dismiss dialog
                        dialog.dismiss();
                    })
                    .create()
                    .show();
        });

        FloatingActionButton fab = findViewById(R.id.fabAddCourse);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CreateCourseActivity.class));
        });
    }

    /*private void seedCourses() {
        List<Course> samples = Arrays.asList(
                new Course("CO2124", "Introduction to Programming", "Alice Smith"),
                new Course("CO2201", "Data Structures",           "Bob Johnson"),
                new Course("CO2300", "Databases",                 "Carol White"),
                new Course("CO2405", "Operating Systems",         "David Brown")
        );
        for (Course c : samples) {
            courseViewModel.insert(c);
        }
    }*/


}

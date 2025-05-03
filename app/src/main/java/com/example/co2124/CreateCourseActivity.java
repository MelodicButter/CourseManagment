package com.example.co2124;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.co2124.viewmodel.CreateCourseViewModel;

public class CreateCourseActivity extends AppCompatActivity {

    private EditText etCode, etName, etLecturer;
    private Button btnCreate;
    private CreateCourseViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        etCode     = findViewById(R.id.etCourseCode);
        etName     = findViewById(R.id.etCourseName);
        etLecturer = findViewById(R.id.etLecturerName);
        btnCreate  = findViewById(R.id.btnCreate);

        // Use the CreateCourseViewModel (not the generic CourseViewModel)
        viewModel = new ViewModelProvider(this)
                .get(CreateCourseViewModel.class);

        btnCreate.setOnClickListener(v -> createCourse());
    }

    private void createCourse() {
        String code     = etCode.getText().toString().trim();
        String name     = etName.getText().toString().trim();
        String lecturer = etLecturer.getText().toString().trim();

        if (TextUtils.isEmpty(code)
                || TextUtils.isEmpty(name)
                || TextUtils.isEmpty(lecturer)) {
            Toast.makeText(this,
                            "All fields are required",
                            Toast.LENGTH_SHORT)
                    .show();
            return;
        }


        viewModel.createCourse(code, name, lecturer)
                .observe(this, success -> {
                    if (Boolean.TRUE.equals(success)) {
                        Toast.makeText(this,
                                        "Course created",
                                        Toast.LENGTH_SHORT)
                                .show();
                        finish();
                    } else {
                        Toast.makeText(this,
                                        "Course code already exists. Pick a different code.",
                                        Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }
}

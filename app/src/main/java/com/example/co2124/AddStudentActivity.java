package com.example.co2124;
import com.example.co2124.AddStudentActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.co2124.viewmodel.AddStudentViewModel;

public class AddStudentActivity extends AppCompatActivity {
    private EditText etName, etEmail, etMatricNumber;
    private AddStudentViewModel viewModel;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etName         = findViewById(R.id.etName);
        etEmail        = findViewById(R.id.etEmail);
        etMatricNumber = findViewById(R.id.etMatricNumber);
        Button btnAdd  = findViewById(R.id.btnAdd);

        courseId = getIntent().getIntExtra(
                CourseDetailsActivity.EXTRA_COURSE_ID, -1
        );

        viewModel = new ViewModelProvider(this)
                .get(AddStudentViewModel.class);

        btnAdd.setOnClickListener(v -> {
            String name         = etName.getText().toString().trim();
            String email        = etEmail.getText().toString().trim();
            String matricNumber = etMatricNumber.getText().toString().trim();

            if (TextUtils.isEmpty(name)
                    || TextUtils.isEmpty(email)
                    || TextUtils.isEmpty(matricNumber)) {
                Toast.makeText(this,
                        "All fields are required",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            viewModel.enroll(courseId, name, email, matricNumber)
                    .observe(this, success -> {
                        if (Boolean.FALSE.equals(success)) {
                            Toast.makeText(this,
                                    "Student already enrolled",
                                    Toast.LENGTH_SHORT
                            ).show();
                        } else {
                            Toast.makeText(this,
                                    "Student added",
                                    Toast.LENGTH_SHORT
                            ).show();
                            finish();
                        }
                    });
        });
    }
}

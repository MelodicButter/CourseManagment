package com.example.co2124;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.co2124.model.Student;
import com.example.co2124.viewmodel.EditStudentViewModel;

public class EditStudentActivity extends AppCompatActivity {
    private EditStudentViewModel vm;
    private EditText etName, etEmail, etMatric;
    private int studentId, courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        etName  = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMatric= findViewById(R.id.etMatricNumber);
        Button btnSave = findViewById(R.id.btnSave);

        studentId = getIntent().getIntExtra("studentId", -1);
        courseId  = getIntent().getIntExtra("courseId", -1);
        vm = new ViewModelProvider(this).get(EditStudentViewModel.class);

        //pre-populate
        vm.getStudent(studentId).observe(this, student -> {
            if (student != null) {
                etName.setText(student.name);
                etEmail.setText(student.email);
                etMatric.setText(student.matricNumber);
            }
        });

        //save updates
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email= etEmail.getText().toString().trim();
            String matric=etMatric.getText().toString().trim();
            if (TextUtils.isEmpty(name) ||
                    TextUtils.isEmpty(email)||
                    TextUtils.isEmpty(matric)) {
                Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
                return;
            }
            Student updated = new Student(name, email, matric);
            updated.studentId = studentId;
            vm.updateStudent(updated);
            Toast.makeText(this, "Student updated", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

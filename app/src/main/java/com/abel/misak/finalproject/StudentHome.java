package com.abel.misak.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.abel.misak.finalproject.login.Student_Login;
import com.google.firebase.auth.FirebaseAuth;

public class StudentHome extends AppCompatActivity {
    private Button student_profile, exit_app, exam_result, clearance_status;
    private FirebaseAuth mAuth;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        student_profile = findViewById(R.id.bt_my_profile);
        exit_app = findViewById(R.id.bt_logout);
        exam_result = findViewById(R.id.bt_exam_result);
        clearance_status = findViewById(R.id.bt_clearance_status);


        try{
            mAuth = FirebaseAuth.getInstance();
            user_id= mAuth.getCurrentUser().getUid();
        } catch (Exception e){
            e.printStackTrace();
        }



        student_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ssignup = new Intent(StudentHome.this, MyProfile.class);
                startActivity(ssignup);

            }
        });

        exam_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exam_result = new Intent(StudentHome.this, ExamResult.class);
                startActivity(exam_result);
            }
        });
        clearance_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent status = new Intent(StudentHome.this, ClearanceStatus.class);
                startActivity(status);
            }
        });
        exit_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent ssignup = new Intent(StudentHome.this, Student_Login.class);
                ssignup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ssignup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ssignup);
                finish();
            }
        });

    }
}

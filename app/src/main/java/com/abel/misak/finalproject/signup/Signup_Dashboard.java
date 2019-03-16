package com.abel.misak.finalproject.signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.abel.misak.finalproject.Dashboard;
import com.abel.misak.finalproject.R;
import com.abel.misak.finalproject.login.Login_dashboard;
import com.abel.misak.finalproject.login.Student_Login;

public class Signup_Dashboard extends AppCompatActivity {
    private Button student_signup, signitory_signup;
    private TextView already_signed_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_dashboard);

        student_signup = findViewById(R.id.bt_student_signup);
        signitory_signup = findViewById(R.id.bt_signatory_signup);
        already_signed_up = findViewById(R.id.tv_have_login);


        student_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent student_sup = new Intent(Signup_Dashboard.this, Student_Signup.class);
                startActivity(student_sup);
                finish();
            }
        });

        signitory_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signatory_sup = new Intent(Signup_Dashboard.this,SignatorySignup.class);
                startActivity(signatory_sup);
                finish();
            }
        });


        already_signed_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent login_intent = new Intent(Signup_Dashboard.this, Login_dashboard.class);
                startActivity(login_intent);
                finish();
            }
        });
    }
}

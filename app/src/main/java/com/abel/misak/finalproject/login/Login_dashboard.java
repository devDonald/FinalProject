package com.abel.misak.finalproject.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.abel.misak.finalproject.R;
import com.abel.misak.finalproject.signup.Signup_Dashboard;
import com.abel.misak.finalproject.signup.Student_Signup;

public class Login_dashboard extends AppCompatActivity {
    private Button bt_student_login, bt_signatory_login;
    private TextView no_signin_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dashboard);

        bt_signatory_login = findViewById(R.id.bt_signatory_login);
        bt_student_login = findViewById(R.id.bt_student_login);
        no_signin_up = findViewById(R.id.tv_no_login);

        bt_student_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent student_sup = new Intent(Login_dashboard.this, Student_Login.class);
                startActivity(student_sup);
                finish();
            }
        });


        bt_signatory_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent student_sup = new Intent(Login_dashboard.this, Signatory_Login.class);
                startActivity(student_sup);
                finish();
            }
        });

        no_signin_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent login_intent = new Intent(Login_dashboard.this, Signup_Dashboard.class);
                startActivity(login_intent);
                finish();
            }
        });
    }
}

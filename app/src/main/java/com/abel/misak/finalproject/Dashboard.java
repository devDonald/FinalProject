package com.abel.misak.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.abel.misak.finalproject.login.Login_dashboard;
import com.abel.misak.finalproject.signup.Signup_Dashboard;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {
    private Button bt_login, bt_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        bt_login = findViewById(R.id.bt_dashboard_login);

        bt_signup = findViewById(R.id.bt_dashboard_signup);


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent login_intent = new Intent(Dashboard.this, Login_dashboard.class);
                startActivity(login_intent);
                
            }
        });


        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signup_intent = new Intent(Dashboard.this, Signup_Dashboard.class);
                startActivity(signup_intent);
            }
        });
    }
}

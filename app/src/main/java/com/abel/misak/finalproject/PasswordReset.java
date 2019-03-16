package com.abel.misak.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abel.misak.finalproject.login.Login_dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.valdesekamdem.library.mdtoast.MDToast;

public class PasswordReset extends AppCompatActivity {
    private EditText inputEmail;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        inputEmail = findViewById(R.id.email);
        btnReset = findViewById(R.id.btn_reset_password);
        btnBack = findViewById(R.id.btn_back);
        auth = FirebaseAuth.getInstance();

        //shows the progress bar
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Resetting Password...")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(Color.BLACK)
                .setAutoDismiss(true);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PasswordReset.this, Login_dashboard.class));
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    MDToast.makeText(getApplicationContext(),"Enter your registered email id",
                            MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                    return;
                }
                hud.show();
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                hud.dismiss();
                                if (task.isSuccessful()) {
                                    MDToast.makeText(getApplication(),"Check your email to reset your password",
                                            MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                                    startActivity(new Intent(PasswordReset.this, Login_dashboard.class));
                                    finish();
                                } else {
                                    MDToast.makeText(getApplication(),"Failed to send reset Email",
                                            MDToast.LENGTH_LONG, MDToast.TYPE_ERROR).show();                                }

                            }
                        });
            }
        });


    }
}

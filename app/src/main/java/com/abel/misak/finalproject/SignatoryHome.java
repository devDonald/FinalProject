package com.abel.misak.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.abel.misak.finalproject.login.Signatory_Login;
import com.abel.misak.finalproject.login.Student_Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignatoryHome extends AppCompatActivity {
    private Button clear_student, update_result,exit_app;
    private FirebaseAuth mAuth;
    private DatabaseReference db_signatory;
    private String staff_id,staff;
    private TextView welcome_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signatory_home);

        clear_student = findViewById(R.id.bt_signatory_home_clear_student);
        update_result = findViewById(R.id.bt_signatory_home_add_result);
        exit_app = findViewById(R.id.bt_signatory_home_exit);
        welcome_note = findViewById(R.id.signatory_welcome);


        db_signatory = FirebaseDatabase.getInstance().getReference().child("Signatories");
        try{
            mAuth = FirebaseAuth.getInstance();
            staff_id = mAuth.getCurrentUser().getUid();
        }catch (Exception e){
            e.printStackTrace();
        }


        db_signatory.child(staff_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String names = dataSnapshot.child("names").getValue().toString();
                        welcome_note.setText(" Welcome "+names);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        clear_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent clear_student = new Intent(SignatoryHome.this, AllStudents2.class);
                startActivity(clear_student);

            }
        });

        update_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update_result = new Intent(SignatoryHome.this, AllStudents.class);
                startActivity(update_result);

            }
        });

        exit_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                Intent ssignup = new Intent(SignatoryHome.this, Signatory_Login.class);
                ssignup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ssignup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ssignup);
                finish();
            }
        });
    }
}

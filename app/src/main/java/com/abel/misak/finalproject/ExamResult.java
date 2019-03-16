package com.abel.misak.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.abel.misak.finalproject.model.StudentModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExamResult extends AppCompatActivity {
    private TextView names,department,course,faculty, session, matric;
    private TextView current_gpa, final_cgpa, due, carry_over;
    private CircleImageView profile;
    private Button check_status;
    private DatabaseReference db_students;
    private String user_id;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result);


        names = findViewById(R.id.exam_result_fullname);
        department = findViewById(R.id.exam_result_department);
        course = findViewById(R.id.exam_result_course);
        faculty = findViewById(R.id.exam_result_faculty);
        session = findViewById(R.id.exam_result_session);
        matric = findViewById(R.id.exam_result_matric_no);
        current_gpa = findViewById(R.id.exam_result_current_gpa);
        final_cgpa = findViewById(R.id.exam_result_final_cgpa);
        due = findViewById(R.id.exam_result_due_clearance);
        carry_over = findViewById(R.id.exam_result_carry_over);
        profile = findViewById(R.id.exam_result_image);
        check_status = findViewById(R.id.bt_exam_result_check_status);

        db_students = FirebaseDatabase.getInstance().getReference().child("Students");
        try{
            mAuth = FirebaseAuth.getInstance();
            user_id= mAuth.getCurrentUser().getUid();
        }catch (Exception e){
            e.printStackTrace();
        }



        db_students.child(user_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            StudentModel studentModel = dataSnapshot.getValue(StudentModel.class);
                            if (dataSnapshot.hasChild("photoUrl")){
                                try {
                                    names.setText(studentModel.getNames());
                                    department.setText(studentModel.getDepartment());
                                    course.setText(studentModel.getCourse());
                                    faculty.setText(studentModel.getFaculty());
                                    session.setText(studentModel.getSession());
                                    matric.setText(studentModel.getMatric_no());
                                    if (dataSnapshot.hasChild("current_gpa")){
                                        current_gpa.setText(studentModel.getCurrent_gpa());
                                    }
                                    if (dataSnapshot.hasChild("cgpa")){
                                        final_cgpa.setText(studentModel.getCgpa());
                                    }
                                    if (dataSnapshot.hasChild("due")){
                                        due.setText(studentModel.getDue());
                                    }

                                    if (dataSnapshot.hasChild("carry_over")){
                                        carry_over.setText(studentModel.getCarry_over());
                                    }
                                    Picasso.get().load(studentModel.getPhotoUrl()).into(profile);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }


                            } else{

                                try {
                                    names.setText(studentModel.getNames());
                                    department.setText(studentModel.getDepartment());
                                    course.setText(studentModel.getCourse());
                                    faculty.setText(studentModel.getFaculty());
                                    session.setText(studentModel.getSession());
                                    matric.setText(studentModel.getMatric_no());
                                    if (dataSnapshot.hasChild("current_gpa")){
                                        current_gpa.setText(studentModel.getCurrent_gpa());
                                    }
                                    if (dataSnapshot.hasChild("cgpa")){
                                        final_cgpa.setText(studentModel.getCgpa());
                                    }
                                    if (dataSnapshot.hasChild("due")){
                                        due.setText(studentModel.getDue());
                                    }

                                    if (dataSnapshot.hasChild("carry_over")){
                                        carry_over.setText(studentModel.getCarry_over());
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        check_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent status = new Intent(ExamResult.this, ClearanceStatus.class);
                startActivity(status);
            }
        });
    }
}

package com.abel.misak.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.abel.misak.finalproject.login.Student_Login;
import com.abel.misak.finalproject.model.StudentModel;
import com.abel.misak.finalproject.signup.Student_Signup;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddExamResult extends AppCompatActivity {
    private TextView names,department,course,faculty, session, matric;
    private EditText current_gpa, final_cgpa;
    private Spinner due, carry_over;
    private CircleImageView profile;
    private Button update_result;
    private DatabaseReference db_students,db_signatory;
    private String student_id,staff_id;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam_result);

        try {
            student_id =getIntent().getExtras().getString("student_id");

        } catch (Exception e){
            e.printStackTrace();
        }

        names = findViewById(R.id.addexam_result_fullname);
        department = findViewById(R.id.addexam_result_department);
        course = findViewById(R.id.addexam_result_course);
        faculty = findViewById(R.id.addexam_result_faculty);
        session = findViewById(R.id.addexam_result_session);
        matric = findViewById(R.id.addexam_result_matric_no);
        current_gpa = findViewById(R.id.addexam_result_current_gpa);
        final_cgpa = findViewById(R.id.addexam_result_final_cgpa);
        due = findViewById(R.id.addexam_result_due_clearance);
        carry_over = findViewById(R.id.addexam_result_carry_over);
        profile = findViewById(R.id.addexam_result_image);
        update_result = findViewById(R.id.bt_add_exams_update);

        db_students = FirebaseDatabase.getInstance().getReference().child("Students");
        try{
            mAuth = FirebaseAuth.getInstance();
        }catch (Exception e){
            e.printStackTrace();
        }


        db_students.child(student_id)
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

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        databaseError.getMessage();
                    }}

                    );


        update_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String send_cgpa = final_cgpa.getText().toString().trim();
                String send_gpa = current_gpa.getText().toString().trim();
                String send_due = due.getItemAtPosition(due.getSelectedItemPosition()).toString().trim();
                String send_carry_over = carry_over.getItemAtPosition(carry_over.getSelectedItemPosition()).toString().trim();

                if (TextUtils.isEmpty(send_cgpa)){
                    final_cgpa.setError("please input cgpa");
                } else if(TextUtils.isEmpty(send_gpa)){
                    current_gpa.setError("please input gpa");
                } else {

                    db_students.child(student_id)
                            .child("cgpa").setValue(send_cgpa);

                    db_students.child(student_id)
                            .child("current_gpa").setValue(send_gpa);
                    db_students.child(student_id)
                            .child("due").setValue(send_due);
                    db_students.child(student_id)
                            .child("carry_over").setValue(send_carry_over).addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    MDToast.makeText(getApplicationContext(),"update successful"
                                            ,MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
                                    Intent ssignup = new Intent(AddExamResult.this, SignatoryHome.class);
                                    ssignup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    ssignup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(ssignup);
                                    finish();
                                }
                            }
                    );
                }
            }
        });
    }
}

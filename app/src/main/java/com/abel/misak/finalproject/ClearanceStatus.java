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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClearanceStatus extends AppCompatActivity {

    private TextView names,department,course,faculty, session, matric;
    private TextView current_gpa, final_cgpa, due, carry_over;
    private CircleImageView profile;
    private DatabaseReference db_students;
    private String user_id;
    private FirebaseAuth mAuth;
    private TextView bursar,academic, security;
    private TextView hod, hostel, liberian;
    private TextView medical, sao, sport, view_remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clearance_status);

        names = findViewById(R.id.sclear_fullname);
        department = findViewById(R.id.sclear_department);
        course = findViewById(R.id.sclear_course);
        faculty = findViewById(R.id.sclear_faculty);
        session = findViewById(R.id.sclear_session);
        matric = findViewById(R.id.sclear_matric_no);
        current_gpa = findViewById(R.id.sclear_current_gpa);
        final_cgpa = findViewById(R.id.sclear_final_cgpa);
        due = findViewById(R.id.sclear_due_clearance);
        carry_over = findViewById(R.id.sclear_carry_over);
        profile = findViewById(R.id.sclear_image);

        bursar = findViewById(R.id.sclear_bursar);
        academic = findViewById(R.id.sclear_academic);
        security = findViewById(R.id.sclear_security);
        hod = findViewById(R.id.sclear_hod);
        hostel = findViewById(R.id.sclear_hostel);
        liberian = findViewById(R.id.sclear_liberian);
        medical = findViewById(R.id.sclear_medical);
        sao = findViewById(R.id.sclear_sao);
        sport = findViewById(R.id.sclear_sport);
        view_remark = findViewById(R.id.bt_view_remark);

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

                                    //capture signatories
                                    if (dataSnapshot.hasChild("bursar")){
                                        bursar.setText(studentModel.getBursar());
                                    }
                                    if (dataSnapshot.hasChild("sao")){
                                        sao.setText(studentModel.getSao());
                                    }
                                    if (dataSnapshot.hasChild("liberian")){
                                        liberian.setText(studentModel.getLiberian());
                                    }

                                    if (dataSnapshot.hasChild("medical")){
                                        medical.setText(studentModel.getMedical());
                                    }
                                    if (dataSnapshot.hasChild("sport")){
                                        sport.setText(studentModel.getSport());
                                    }
                                    if (dataSnapshot.hasChild("security")){
                                        security.setText(studentModel.getSecurity());
                                    }
                                    if (dataSnapshot.hasChild("academic")){
                                        academic.setText(studentModel.getAcademic());
                                    }

                                    if (dataSnapshot.hasChild("hostel")){
                                        hostel.setText(studentModel.getHostel());
                                    }
                                    if (dataSnapshot.hasChild("hod")){
                                        hod.setText(studentModel.getHod());
                                    }
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


                                    //capture signatories
                                    if (dataSnapshot.hasChild("bursar")){
                                        bursar.setText(studentModel.getBursar());
                                    }
                                    if (dataSnapshot.hasChild("sao")){
                                        sao.setText(studentModel.getSao());
                                    }
                                    if (dataSnapshot.hasChild("liberian")){
                                        liberian.setText(studentModel.getLiberian());
                                    }

                                    if (dataSnapshot.hasChild("medical")){
                                        medical.setText(studentModel.getMedical());
                                    }
                                    if (dataSnapshot.hasChild("sport")){
                                        sport.setText(studentModel.getSport());
                                    }
                                    if (dataSnapshot.hasChild("security")){
                                        security.setText(studentModel.getSecurity());
                                    }
                                    if (dataSnapshot.hasChild("academic")){
                                        academic.setText(studentModel.getAcademic());
                                    }

                                    if (dataSnapshot.hasChild("hostel")){
                                        hostel.setText(studentModel.getHostel());
                                    }
                                    if (dataSnapshot.hasChild("hod")){
                                        hod.setText(studentModel.getHod());
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            if (studentModel.getAcademic().matches("Yes")&&
                            studentModel.getBursar().matches("Yes")&&
                            studentModel.getHod().matches("Yes")&&
                            studentModel.getHostel().matches("Yes")&&
                            studentModel.getLiberian().matches("Yes")&&
                            studentModel.getMedical().matches("Yes")&&
                            studentModel.getSao().matches("Yes")&&
                            studentModel.getSecurity().matches("Yes")&&
                            studentModel.getSport().matches("Yes")){
                                view_remark.setVisibility(View.VISIBLE);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}

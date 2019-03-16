package com.abel.misak.finalproject;

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

public class ClearStudent extends AppCompatActivity {
    private TextView names,department,course,faculty, session, matric;
    private TextView current_gpa, final_cgpa, due, carry_over;
    private TextView bursar_yes,bursar_no,academic_yes,academic_no, security_yes, security_no;
    private TextView hod_yes, hod_no, hostel_yes,hostel_no, liberian_yes,liberian_no;
    private TextView medical_yes,medical_no, sao_yes,sao_no, sport_yes, sport_no;
    private CircleImageView profile;
    private Button check_status;
    private DatabaseReference db_students;
    private String student_id;
    private FirebaseAuth mAuth;

    private TextView bursar,academic, security;
    private TextView hod, hostel, liberian;
    private TextView medical, sao, sport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_student);

        try {
            student_id =getIntent().getExtras().getString("student_id");

        } catch (Exception e){
            e.printStackTrace();
        }

        names = findViewById(R.id.clear_fullname);
        department = findViewById(R.id.clear_department);
        course = findViewById(R.id.clear_course);
        faculty = findViewById(R.id.clear_faculty);
        session = findViewById(R.id.clear_session);
        matric = findViewById(R.id.clear_matric_no);
        current_gpa = findViewById(R.id.clear_current_gpa);
        final_cgpa = findViewById(R.id.clear_final_cgpa);
        due = findViewById(R.id.clear_due_clearance);
        carry_over = findViewById(R.id.clear_carry_over);
        profile = findViewById(R.id.clear_image);

        //link signatories
        bursar_yes = findViewById(R.id.clear_bursar_yes);
        bursar_no = findViewById(R.id.clear_bursar_no);
        academic_yes = findViewById(R.id.clear_academic_yes);
        academic_no = findViewById(R.id.clear_academic_no);
        security_yes = findViewById(R.id.clear_security_yes);
        security_no = findViewById(R.id.clear_security_no);
        hod_yes = findViewById(R.id.clear_hod_yes);
        hod_no = findViewById(R.id.clear_hod_no);
        hostel_yes = findViewById(R.id.clear_hostel_yes);
        hostel_no = findViewById(R.id.clear_hostel_no);
        liberian_yes = findViewById(R.id.clear_liberian_yes);
        liberian_no = findViewById(R.id.clear_liberian_no);
        medical_yes = findViewById(R.id.clear_medical_yes);
        medical_no = findViewById(R.id.clear_medical_no);
        sao_yes = findViewById(R.id.clear_sao_yes);
        sao_no = findViewById(R.id.clear_sao_no);
        sport_yes = findViewById(R.id.clear_sport_yes);
        sport_no = findViewById(R.id.clear_sport_no);

        bursar = findViewById(R.id.cclear_bursar);
        academic = findViewById(R.id.cclear_academic);
        security = findViewById(R.id.cclear_security);
        hod = findViewById(R.id.cclear_hod);
        hostel = findViewById(R.id.cclear_hostel);
        liberian = findViewById(R.id.cclear_liberian);
        medical = findViewById(R.id.cclear_medical);
        sao = findViewById(R.id.cclear_sao);
        sport = findViewById(R.id.cclear_sport);

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
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        //set onclick listenners on all the signatories responses

        bursar_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("bursar").setValue("Yes");
                resetViews();
                bursar_yes.setText("Yes");
            }
        });
        bursar_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("bursar").setValue("No");

                resetViews();
                bursar_no.setText("No");
            }
        });

        academic_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("academic").setValue("Yes");

                resetViews();
                academic_yes.setText("Yes");
            }
        });
        academic_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db_students.child(student_id).child("academic").setValue("no");

                resetViews();
                academic_no.setText("No");
            }
        });
        security_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db_students.child(student_id).child("security").setValue("Yes");
                resetViews();
                security_yes.setText("Yes");
            }
        });
        security_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("security").setValue("No");

                resetViews();
                security_no.setText("No");
            }
        });

        hod_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("hod").setValue("Yes");
                resetViews();
                hod_yes.setText("Yes");
            }
        });
        hod_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("hod").setValue("No");
                resetViews();
                hod_no.setText("No");
            }
        });
        hostel_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("hostel").setValue("Yes");
                resetViews();
                hostel_yes.setText("Yes");
            }
        });
        hostel_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("hostel").setValue("No");
                resetViews();
                hostel_no.setText("No");
            }
        });
        liberian_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("liberian").setValue("Yes");

                resetViews();
                liberian_yes.setText("Yes");
            }
        });
        liberian_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("liberian").setValue("No");

                resetViews();
                liberian_no.setText("No");
            }
        });
        medical_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("medical").setValue("Yes");
                resetViews();
                medical_yes.setText("Yes");
            }
        });
        medical_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db_students.child(student_id).child("medical").setValue("No");
                resetViews();
                medical_no.setText("No");

            }
        });
        sao_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("sao").setValue("Yes");
                resetViews();
                sao_yes.setText("Yes");
            }
        });
        sao_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("sao").setValue("No");
                resetViews();
                sao_no.setText("No");
            }
        });

        sport_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("sport").setValue("Yes");

                resetViews();
                sport_yes.setText("yes");
            }
        });
        sport_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_students.child(student_id).child("sport").setValue("NO");
                resetViews();
                sport_no.setText("No");
            }
        });

    }

    public void resetViews(){
        //set other views to null
        bursar_no.setText("");
        bursar_yes.setText("");
        academic_yes.setText("");
        academic_no.setText("");
        security_yes.setText("");
        security_no.setText("");
        hod_no.setText("");
        hod_yes.setText("");
        hostel_yes.setText("");
        hostel_no.setText("");
        liberian_yes.setText("");
        liberian_no.setText("");
        medical_no.setText("");
        medical_yes.setText("");
        sao_no.setText("");
        sao_yes.setText("");
        sport_no.setText("");
        sport_yes.setText("");
    }
}

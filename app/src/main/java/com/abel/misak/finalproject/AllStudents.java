package com.abel.misak.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abel.misak.finalproject.model.StudentModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllStudents extends AppCompatActivity {
    private RecyclerView mUsersRecycler;

    private DatabaseReference mUsersDatabase;
    private String uid;
    private FirebaseUser mUsers;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);

        mUsersRecycler = findViewById(R.id.all_students_recycler);
        mUsersRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Students");

        try {
            mAuth=FirebaseAuth.getInstance();
            mUsers = mAuth.getCurrentUser();
            uid = mUsers.getUid();
            displayUsers();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView student_name,student_department, student_matric;
        CircleImageView student_image;

        public MyViewHolder(View itemView) {
            super(itemView);

            student_name = itemView.findViewById(R.id.all_student_name);
            student_department = itemView.findViewById(R.id.all_student_department);
            //rvStatus = itemView.findViewById(R.id.all_users_status);
            student_matric = itemView.findViewById(R.id.all_student_matric_no);
            student_image = itemView.findViewById(R.id.all_student_image);
        }


    }

    public void displayUsers(){
        final FirebaseRecyclerOptions<StudentModel> options =
                new FirebaseRecyclerOptions.Builder<StudentModel>()
                        .setQuery(mUsersDatabase, StudentModel.class)
                        .build();

        FirebaseRecyclerAdapter<StudentModel,MyViewHolder> adapter = new
                FirebaseRecyclerAdapter<StudentModel, MyViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(final MyViewHolder holder, final int position, final StudentModel model) {
                        try {
                            mUsersDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){

                                        if (dataSnapshot.hasChild("photoUrl")){

                                            holder.student_name.setText(model.getNames());
                                            holder.student_matric.setText(model.getMatric_no());
                                            holder.student_department.setText(model.getDepartment());
                                            Picasso.get().load(model.getPhotoUrl())
                                                    .into(holder.student_image);
                                        } else {

                                            holder.student_name.setText(model.getNames());
                                            holder.student_matric.setText(model.getMatric_no());
                                            holder.student_department.setText(model.getDepartment());
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String student_id= getRef(position).getKey();
                                    Intent all_students = new Intent(getApplicationContext(), AddExamResult.class);
                                    all_students.putExtra("student_id",student_id);
                                    startActivity(all_students);

                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @NonNull
                    @Override
                    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_all_students,parent,false);

                        return new MyViewHolder(view);
                    }
                };
        mUsersRecycler.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

}

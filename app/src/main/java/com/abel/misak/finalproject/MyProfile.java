package com.abel.misak.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abel.misak.finalproject.model.StudentModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends AppCompatActivity {
    private TextView tv_name, tv_phone,tv_matric_no, tv_department, tv_faculty;
    private CircleImageView myProfile;
    private ImageView change_profile;
    private FirebaseAuth mAuth;
    private StorageReference imageReference;
    private static final int GALLERY_REQUEST =2;
    private Uri imageUri =null;
    private KProgressHUD hud;
    private String user_id;
    private DatabaseReference db_students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        tv_name = findViewById(R.id.mp_name);
        tv_phone = findViewById(R.id.mp_phone);
        tv_matric_no = findViewById(R.id.mp_matric_no);
        tv_department = findViewById(R.id.mp_department);
        tv_faculty = findViewById(R.id.mp_faculty);
        myProfile = findViewById(R.id.mprofile_image);
        change_profile = findViewById(R.id.m_change_profile_image);

        db_students = FirebaseDatabase.getInstance().getReference().child("Students");
        imageReference = FirebaseStorage.getInstance().getReference();

        try {
            mAuth = FirebaseAuth.getInstance();
            user_id= mAuth.getCurrentUser().getUid();
        } catch (Exception e){
            e.printStackTrace();
        }

        hud = KProgressHUD.create(MyProfile.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait")
                .setDetailsLabel("Uploading Profile picture...")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(Color.BLACK)
                .setAutoDismiss(true);

        db_students.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    StudentModel model = dataSnapshot.getValue(StudentModel.class);
                    try {

                        if (dataSnapshot.hasChild("photoUrl")){

                            Picasso.get()
                                    .load(dataSnapshot.child("photoUrl").getValue().toString())
                                    .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                                    .into(myProfile);
                            tv_name.setText("Name: "+model.getNames());
                            tv_department.setText("Department: "+model.getDepartment());
                            tv_faculty.setText("Faculty: "+model.getFaculty());
                            tv_matric_no.setText("Matric No: "+model.getMatric_no());
                            tv_phone.setText("Phone: "+model.getPhone());
                        } else {
                            tv_name.setText(model.getNames());
                            tv_department.setText(model.getDepartment());
                            tv_faculty.setText(model.getFaculty());
                            tv_matric_no.setText(model.getMatric_no());
                            tv_phone.setText(model.getPhone());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        change_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(galIntent, "Choose picture"), GALLERY_REQUEST);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {
            imageUri = data.getData();


            try {
                if (imageUri!=null){
                    hud.show();
                    final StorageReference filePath = imageReference.child("images").child(user_id).child("myImage.jpg");

//                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
//                    byte[] datas = baos.toByteArray();
                    UploadTask uploadTask = filePath.putFile(imageUri);
                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            hud.dismiss();
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(MyProfile.this, new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            hud.dismiss();
                            if (task.isSuccessful()){
                                Uri downloadUrl = task.getResult();

                                db_students.child(user_id).child("photoUrl").setValue(downloadUrl.toString());

                                MDToast mdToast = MDToast.makeText(getApplicationContext(),
                                        "Profile image Updated successfully!",
                                        MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS);
                                mdToast.show();
                                myProfile.setImageURI(imageUri);


                            } else {
                                MDToast mdToast = MDToast.makeText(getApplicationContext(),
                                        "Profile image failed to update!",
                                        MDToast.LENGTH_LONG,MDToast.TYPE_ERROR);
                                mdToast.show();

                            }

                        }
                    });
                } else {
                    MDToast mdToast = MDToast.makeText(getApplicationContext(),
                            "Please Select an image to replace!",
                            MDToast.LENGTH_LONG,MDToast.TYPE_ERROR);
                    mdToast.show();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}

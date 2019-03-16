package com.abel.misak.finalproject.signup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.abel.misak.finalproject.R;
import com.abel.misak.finalproject.login.Signatory_Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.HashMap;
import java.util.Map;

public class SignatorySignup extends AppCompatActivity {
    private EditText et_name, et_staff_id, et_email, et_password, et_confirm_password, et_department;
    private Spinner sp_position;
    private Button submit_button;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference db_signatory;
    private String current_uid, names,email, staff_id, password1, password2, department,position;
    private KProgressHUD hud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signatory_signup);

        db_signatory = FirebaseDatabase.getInstance().getReference().child("Signatories");
        mAuth = FirebaseAuth.getInstance();

        et_name = findViewById(R.id.et_staff_name);
        et_department = findViewById(R.id.et_staff_department);
        et_password = findViewById(R.id.et_staff_password);
        et_confirm_password = findViewById(R.id.et_staff_password2);
        et_staff_id = findViewById(R.id.et_staff_id);
        submit_button = findViewById(R.id.bt_staff_signup);
        et_email = findViewById(R.id.et_staff_email);
        sp_position = findViewById(R.id.et_staff_position);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Registering Signatory...")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(Color.BLACK)
                .setAutoDismiss(true);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                names = et_name.getText().toString().trim();
                email = et_email.getText().toString().trim();
                department = et_department.getText().toString().trim();
                staff_id = et_staff_id.getText().toString().trim();
                password1 = et_password.getText().toString().trim();
                password2 = et_confirm_password.getText().toString().trim();
                position = sp_position.getItemAtPosition(sp_position.getSelectedItemPosition()).toString().trim();

                if (TextUtils.isEmpty(names)){
                    et_name.setError("name is empty");
                } else if(TextUtils.isEmpty(email)||!email.contains("@") || !email.contains(".com")){
                    et_email.setError("invalid email");
                } else if (TextUtils.isEmpty(password1)){
                    et_password.setError("empty password");
                } else if (TextUtils.isEmpty(password2)){
                    et_confirm_password.setError("empty password");
                } else if (password1.length()<6 || password2.length()<6){
                    et_password.setError("password must be 6 or more characters");
                } else if(!password1.matches(password2)){
                    et_confirm_password.setError("password does not match");
                } else if(TextUtils.isEmpty(staff_id)){
                    et_staff_id.setError("empty field");
                } else if (TextUtils.isEmpty(department)){
                    et_department.setError("department empty");
                } else if (position.matches("Select Position")){

                    MDToast.makeText(getApplicationContext(),"Select valid position"
                            ,MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else {
                    if (isNetworkAvailable(getApplicationContext())){
                        hud.show();
                        mAuth.createUserWithEmailAndPassword(email,password1)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        hud.dismiss();
                                        if (task.isSuccessful()){
                                            mUser = mAuth.getCurrentUser();
                                            current_uid = mUser.getUid();

                                            // Create a new user with a first and last name
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("names", names);
                                            user.put("email", email);
                                            user.put("department", department);
                                            user.put("staff_id",staff_id);
                                            user.put("position",position);
                                            user.put("uid",current_uid);

                                            // Add a new document with a generated ID
                                            String id = db_signatory.push().getKey();
                                            db_signatory.child(current_uid).setValue(user)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            MDToast.makeText(getApplicationContext(),"Sign up successful"
                                                                    ,MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();

                                                            Intent ssignup = new Intent(SignatorySignup.this, Signatory_Login.class);
                                                            ssignup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            ssignup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(ssignup);
                                                            finish();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                        }
                                                    });


                                        } else {

                                            MDToast.makeText(getApplicationContext(),"Sign up Failed, Check your info"
                                                    ,MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                                        }
                                    }
                                });
                    }else {

                        MDToast.makeText(getApplicationContext(),"Check your network connectivity"
                                ,MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                    }

                }
            }
        });
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectMgr;
        connectMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectMgr.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

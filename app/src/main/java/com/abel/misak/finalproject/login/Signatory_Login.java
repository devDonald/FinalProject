package com.abel.misak.finalproject.login;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.abel.misak.finalproject.PasswordReset;
import com.abel.misak.finalproject.R;
import com.abel.misak.finalproject.SignatoryHome;
import com.abel.misak.finalproject.StudentHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.valdesekamdem.library.mdtoast.MDToast;

import de.hdodenhof.circleimageview.CircleImageView;

public class Signatory_Login extends AppCompatActivity {

    //declare your global variables and make them private so that only this class will seee them
    private EditText et_email, et_password;
    private Button submit_button;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String current_uid, email, password;
    private KProgressHUD hud;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signatory__login);

        //link the global variables to your xml ui
        et_password = findViewById(R.id.et_signatory_login_password);
        submit_button = findViewById(R.id.bt_login_signatory);
        et_email = findViewById(R.id.et_signatory_login_email);
        forgotPassword = findViewById(R.id.tv_signatory_forgot_password);

        //get an instance of the current user of the app on this device
      try {
          mAuth = FirebaseAuth.getInstance();
          mUser = mAuth.getCurrentUser();
          current_uid = mUser.getUid();
      } catch (Exception e){
          e.printStackTrace();
      }

        if (mUser!=null){

            //check if user has logged into this app before now
            Intent login = new Intent(Signatory_Login.this, SignatoryHome.class);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(login);
            finish();
        }

        //shows the progress bar
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Logging Signatory in...")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(Color.BLACK)
                .setAutoDismiss(true);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = et_email.getText().toString().trim();
                password = et_password.getText().toString().trim();

                if(TextUtils.isEmpty(email)||!email.contains("@") || !email.contains(".com")){
                    et_email.setError("invalid email");
                } else if (TextUtils.isEmpty(password)){
                    et_password.setError("empty password");
                } else if (password.length()<6){
                    et_password.setError("password must be 6 or more characters");
                } else {
                    if (isNetworkAvailable(getApplicationContext())){
                        hud.show();
                        mAuth.signInWithEmailAndPassword(email,password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        hud.dismiss();
                                        if (task.isSuccessful()){
                                            Intent login = new Intent(Signatory_Login.this, SignatoryHome.class);
                                            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                                            startActivity(login);
                                            finish();
                                        } else {

                                            MDToast.makeText(getApplicationContext(),"Login Failed, email/password"
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

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(Signatory_Login.this, PasswordReset.class);
                login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(login);
                finish();
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

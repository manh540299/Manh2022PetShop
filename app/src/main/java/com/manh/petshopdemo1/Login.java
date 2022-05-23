package com.manh.petshopdemo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText edtEmail;
    EditText edtPass;
    Button btnLogin;
    TextView tvSingUp;
    TextView tvForgotPass,tvGoHome;
    ImageView imgback;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        initListener();
    }
    private void initUI() {
        progressDialog=new ProgressDialog(this);
        edtEmail=findViewById(R.id.edtemail);
        edtPass=findViewById(R.id.edtpass);
        btnLogin=findViewById(R.id.btnlogin);
        tvSingUp=findViewById(R.id.tvsignup);
        tvForgotPass=findViewById(R.id.tvforgotpass);
        tvGoHome=findViewById(R.id.tvgohome);
        imgback=findViewById(R.id.imgback);
    }
    private void initListener() {
        tvSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogin();
            }
        });
       tvGoHome.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(Login.this,MainActivity.class);
               startActivity(intent);
               finishAffinity();
           }
       });
       tvForgotPass.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(Login.this,ResetPassword.class);
               startActivity(intent);
           }
       });
    }

    private void onClickLogin() {
        Intent intent=getIntent();
        boolean checkD=intent.getBooleanExtra("detailk",false);
        progressDialog.show();
        String email=edtEmail.getText().toString();
        String password=edtPass.getText().toString();
        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(Login.this,"you have not entered your email or password",Toast.LENGTH_SHORT).show();
        }else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                if(!checkD) {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                }else {
                                    finish();
                                }

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Login.this, "email or password incorrect",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
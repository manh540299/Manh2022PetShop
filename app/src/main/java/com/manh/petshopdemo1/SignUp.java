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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    private EditText edtname;
    private EditText edtpass;
    private EditText edtconfirmpass;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    private ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        initListener();
    }

    private void initUI() {
        edtname = findViewById(R.id.edtemail);
        edtpass = findViewById(R.id.edtpass);
        edtconfirmpass = findViewById(R.id.edtconfirmpass);
        btnSignUp = findViewById(R.id.btnsignup);
        progressDialog=new ProgressDialog(this);
        imgback=findViewById(R.id.imgback);
    }

    private void initListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void onClickSignUp() {
        boolean check = true;
        String email = edtname.getText().toString().trim();
        String password = edtpass.getText().toString().trim();
        String cfpass = edtconfirmpass.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(SignUp.this, "Email can not blank", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (password.length() < 6) {
            check = false;
            Toast.makeText(SignUp.this, "Password should be more than 6 characters", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(cfpass)) {
            check = false;
            Toast.makeText(SignUp.this, "reconfirm password", Toast.LENGTH_SHORT).show();
        }


        if (check) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUp.this, "Account already exists",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}
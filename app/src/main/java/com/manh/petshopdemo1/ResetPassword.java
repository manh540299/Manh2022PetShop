package com.manh.petshopdemo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    private EditText edtemail;
    private Button btnrspass;
    private ProgressDialog dialog;
    private ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initUI();
        initListener();
    }



    private void initUI() {
        imgback=findViewById(R.id.imgback);
        edtemail=findViewById(R.id.edtemail);
        btnrspass=findViewById(R.id.btnrspass);
        dialog=new ProgressDialog(this);
    }
    private void initListener() {
        btnrspass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickResetPass();
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void onClickResetPass() {
        dialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = edtemail.getText().toString();
        if(emailAddress.isEmpty()){
            Toast.makeText(ResetPassword.this,"Please enter your password",Toast.LENGTH_LONG).show();
        }else {
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPassword.this, "Please check your email to change your password", Toast.LENGTH_LONG).show();
                                finish();
                            } else
                                Toast.makeText(ResetPassword.this, "email incorrect", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}
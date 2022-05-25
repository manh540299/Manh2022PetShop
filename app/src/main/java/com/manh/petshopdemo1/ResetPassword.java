package com.manh.petshopdemo1;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
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
        imgback=findViewById(R.id.rs_imgback);
        edtemail=findViewById(R.id.edtemail);
        btnrspass=findViewById(R.id.btnrspass);
        dialog=new ProgressDialog(this);
    }
    private void initListener() {
        btnrspass.setOnClickListener(view -> onClickResetPass());
        imgback.setOnClickListener(view -> finish());
    }

    private void onClickResetPass() {
        dialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = edtemail.getText().toString();
        if(emailAddress.isEmpty()){
            Toast.makeText(ResetPassword.this,"Please enter your gmail",Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }else {
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(task -> {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPassword.this, "Please check your email to change your password", Toast.LENGTH_LONG).show();
                            finish();
                        } else
                            Toast.makeText(ResetPassword.this, "email incorrect", Toast.LENGTH_LONG).show();
                    });
        }
    }
}
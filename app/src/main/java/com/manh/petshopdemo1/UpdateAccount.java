package com.manh.petshopdemo1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.manh.petshopdemo1.fragment.Profile_Fragment;

import java.io.IOException;

public class UpdateAccount extends AppCompatActivity {
    private ImageView imgavatar, imgback;
    private EditText edtusername;
    private Button btnupdate;
    private TextView email;
    private static final int MY_REQUEST_CODE = 10;
    private Uri uri;
    private FirebaseUser user;
    private ProgressDialog dialog;
    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
                if (intent == null)
                    return;
                 uri = intent.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imgavatar.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);
        initUI();
        initListener();
    }


    private void initUI() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        dialog=new ProgressDialog(this);
        imgavatar = findViewById(R.id.imgavartar);
        edtusername = findViewById(R.id.edtusername);
        email = findViewById(R.id.tvemail);
        imgback = findViewById(R.id.imgback);
        btnupdate = findViewById(R.id.btnupdate);
        email.setText(user.getEmail());
    }

    private void initListener() {
        imgavatar.setOnClickListener(view -> onClickRequestPermission());
        imgback.setOnClickListener(view -> finish());
        btnupdate.setOnClickListener(view -> {
            dialog.show();
            onClickUpdateProfile();
        });
    }

    private void onClickUpdateProfile() {


        UserProfileChangeRequest profileUpdates;
        if (user == null)
            return;
        String username=edtusername.getText().toString().trim();
        if(username.isEmpty())
           username=user.getDisplayName();

            if (uri == null) {
                profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .setPhotoUri(user.getPhotoUrl())
                        .build();
            } else {

                profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .setPhotoUri(uri)
                        .build();
            }
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "update profile success", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(UpdateAccount.this,MainActivity.class);
                            intent.putExtra("updateprofile",true);
                            startActivity(intent);
                            finishAffinity();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"update faild",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

    }


    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (UpdateAccount.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            UpdateAccount.this.requestPermissions(permissions, MY_REQUEST_CODE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {

            }
        }
    }
}
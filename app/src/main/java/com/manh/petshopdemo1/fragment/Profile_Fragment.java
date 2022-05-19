package com.manh.petshopdemo1.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manh.petshopdemo1.Login;
import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.SignUp;
import com.manh.petshopdemo1.UpdateAccount;
import com.squareup.picasso.Picasso;

public class Profile_Fragment extends Fragment {
    private FirebaseUser user;
    private ImageView imgacc;
    private Button btnlogout;
    private View view;
    TextView btnlogin, btnsignup, tvusername, tvemail;
    RelativeLayout rlfavorite, rlchangepass, rloder, rlmyprofile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(inflater.getContext());
        view = layoutInflater.inflate(R.layout.activity_profile_fragment, container, false);
        initUI();
        setAccount();
        initListener();
        return view;
    }


    public void initUI() {
        btnlogin = view.findViewById(R.id.btnlogin);
        btnsignup = view.findViewById(R.id.btnsignup);
        btnlogout = view.findViewById(R.id.btnlogout);
        tvusername = view.findViewById(R.id.tvusername);
        tvemail = view.findViewById(R.id.tvemail);
        rlchangepass = view.findViewById(R.id.rlchangepass);
        rlfavorite = view.findViewById(R.id.rlfavorite);
        rlmyprofile = view.findViewById(R.id.rlmyprofile);
        rloder = view.findViewById(R.id.rloder);
        imgacc = view.findViewById(R.id.imgacc);

    }

    public void setAccount() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            btnlogin.setVisibility(View.VISIBLE);
            btnsignup.setVisibility(View.VISIBLE);
            btnlogout.setVisibility(View.GONE);
        } else {
            btnlogin.setVisibility(View.GONE);
            btnsignup.setVisibility(View.GONE);
            String username = user.getDisplayName();
            String email = user.getEmail();
            Uri uri = user.getPhotoUrl();
            if (username == null) {
                tvusername.setVisibility(View.GONE);
            } else {
                tvusername.setVisibility(View.VISIBLE);
            }
            tvusername.setText(username);
            tvemail.setText(email);
            if (uri != null) {
                Picasso.get().load(uri).error(R.drawable.avatar_default).into(imgacc);
            }
        }
    }

    private void initListener() {
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignUp.class);
                startActivity(intent);
            }
        });
        rlmyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user == null) {
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), UpdateAccount.class);
                    startActivity(intent);
                }
            }
        });
        rloder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null) {
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                }
            }
        });
        rlchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null) {

                } else {
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                }
            }
        });
        rlfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null) {

                } else {
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                }
            }
        });
    }
}
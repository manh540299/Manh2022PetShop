package com.manh.petshopdemo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manh.petshopdemo1.databinding.ActivityMainBinding;
import com.manh.petshopdemo1.fragment.Cart_Fagment;
import com.manh.petshopdemo1.fragment.Home_Fragment;
import com.manh.petshopdemo1.fragment.Messenge_Fagment;
import com.manh.petshopdemo1.fragment.Profile_Fragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentTransaction fragmentTransaction;
    private int CurrentFragment = 0;
    private final int NotificationFragment = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setFragDefault();
        binding.botnav.setSelectedItemId(R.id.home);
        setOnBottomNavigation();
        getFragment();

    }

    private void setFragDefault() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainframe, new Home_Fragment());
        fragmentTransaction.commit();
    }

    @SuppressLint("NonConstantResourceId")
    public void setOnBottomNavigation() {


        binding.botnav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    openHomeFragment();
                    break;
                case R.id.cart:
                    openCartFragment();
                    break;
                case R.id.profile:
                    openProfileFragment();
                    break;
//                    case R.id.notification:
//                        openNotificationFragment();
//                        break;
                case R.id.message:

                    openMessengeFragment();
                    break;
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframe, fragment);
        fragmentTransaction.commit();
    }

    public void openHomeFragment() {
        int homeFragment = 1;
        if (CurrentFragment != homeFragment) {
            replaceFragment(new Home_Fragment());
            CurrentFragment= homeFragment;
        }
    }

    public void openCartFragment() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent=new Intent(MainActivity.this,Login.class);
            startActivity(intent);
        }else {
            int cartFragment = 2;
            if (CurrentFragment != cartFragment) {
                replaceFragment(new Cart_Fagment());
                CurrentFragment = cartFragment;
            }
        }
    }

    public void openMessengeFragment() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        } else {
            int messengeFragment = 3;
            if (CurrentFragment != messengeFragment) {
                replaceFragment(new Messenge_Fagment());
                CurrentFragment = messengeFragment;
            }
        }
    }

//    public void openNotificationFragment() {
//        if (CurrentFragment != NotificationFragment) {
//            replaceFragment(new Notification_Fragment());
//            CurrentFragment=NotificationFragment;
//        }
//    }

    public void openProfileFragment() {
        int profileFragment = 5;
        if (CurrentFragment != profileFragment) {
            replaceFragment(new Profile_Fragment());
            CurrentFragment= profileFragment;
        }
    }
    private void openProfile(){
        replaceFragment(new Profile_Fragment());
        binding.botnav.setSelectedItemId(R.id.profile);
    }
    private void openCart(){
        binding.botnav.setSelectedItemId(R.id.cart);
        openCartFragment();
    }
    private void getFragment(){
        Intent intent=getIntent();
        boolean checkUpdateP=intent.getBooleanExtra("updateprofile",false);
        boolean checkC = intent.getBooleanExtra("key", false);
        if(checkUpdateP){
            openProfile();
        }else {
            if (checkC) {
                openCart();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;

    }
}
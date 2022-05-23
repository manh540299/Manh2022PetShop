package com.manh.petshopdemo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
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
    private final int HomeFragment = 1;
    private final int CartFragment = 2;
    private final int MessengeFragment = 3;
    private final int NotificationFragment = 4;
    private final int ProfileFragment = 5;
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

    public void setOnBottomNavigation() {


        binding.botnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
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
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframe, fragment);
        fragmentTransaction.commit();
    }

    public void openHomeFragment() {
        if (CurrentFragment != HomeFragment) {
            replaceFragment(new Home_Fragment());
            CurrentFragment=HomeFragment;
        }
    }

    public void openCartFragment() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent=new Intent(MainActivity.this,Login.class);
            startActivity(intent);
        }else {
            if (CurrentFragment != CartFragment) {
                replaceFragment(new Cart_Fagment());
                CurrentFragment = CartFragment;
            }
        }
    }

    public void openMessengeFragment() {
        if (CurrentFragment != MessengeFragment) {
            replaceFragment(new Messenge_Fagment());
            CurrentFragment=MessengeFragment;
        }
    }

//    public void openNotificationFragment() {
//        if (CurrentFragment != NotificationFragment) {
//            replaceFragment(new Notification_Fragment());
//            CurrentFragment=NotificationFragment;
//        }
//    }

    public void openProfileFragment() {
        if (CurrentFragment != ProfileFragment) {
            replaceFragment(new Profile_Fragment());
            CurrentFragment=ProfileFragment;
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


}
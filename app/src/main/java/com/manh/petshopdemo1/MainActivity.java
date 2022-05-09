package com.manh.petshopdemo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.manh.petshopdemo1.databinding.ActivityMainBinding;
import com.manh.petshopdemo1.fragment.Cart_Fagment;
import com.manh.petshopdemo1.fragment.Home_Fragment;
import com.manh.petshopdemo1.fragment.Messenge_Fagment;
import com.manh.petshopdemo1.fragment.Notification_Fragment;
import com.manh.petshopdemo1.fragment.Profile_Fragment;

public class MainActivity extends AppCompatActivity {
   ActivityMainBinding binding;
   FragmentTransaction fragmentTransaction;
   private  int CurrentFragment=0;
   private int HomeFragment=1;
    private int CartFragment=2;
    private int MessengeFragment=3;
    private int NotificationFragment=4;
    private int ProfileFragment=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainframe,new Home_Fragment());
        fragmentTransaction.commit();
        binding.botnav.setSelectedItemId(R.id.home);
        setOnBottomNavigation();

    }
    private void setOnBottomNavigation(){
        binding.botnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        openHomeFragment();
                    break;
                    case R.id.cart:
                        openCartFragment();
                        break;
                    case R.id.profile:
                        openProfileFragment();
                        break;
                    case R.id.notification:
                        openNotificationFragment();
                        break;
                    case R.id.message:

                        openMessengeFragment();
                        break;
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainframe, fragment);
            fragmentTransaction.commit();
    }
    private void openHomeFragment(){
        if(CurrentFragment!=HomeFragment) {
           replaceFragment(new Home_Fragment());
        }
    }
    private void openCartFragment(){
        if(CurrentFragment!=CartFragment) {
            replaceFragment(new Cart_Fagment());
        }
    }
    private void openMessengeFragment(){
        if(CurrentFragment!=MessengeFragment) {
            replaceFragment(new Messenge_Fagment());
        }
    }
    private void openNotificationFragment(){
        if(CurrentFragment!=NotificationFragment) {
            replaceFragment(new Notification_Fragment());
        }
    }
    private void openProfileFragment(){
        if(CurrentFragment!=ProfileFragment) {
            replaceFragment(new Profile_Fragment());
        }
    }
}
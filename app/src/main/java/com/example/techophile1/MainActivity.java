package com.example.techophile1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ChipNavigationBar cnb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            Intent intent = new Intent(MainActivity.this , Login.class);
            startActivity(intent);
//            finish();
        }
        
        cnb = findViewById(R.id.bottom_nav);
        cnb.setItemSelected(R.id.dashboard, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new Dashboard()).commit();
        bottomMenu();


    }

    private void bottomMenu() {
        cnb.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment frag = null;
                if(i == R.id.dashboard){
                    frag = new Dashboard();
                }
                else if(i == R.id.chat){
                    frag = new Chat();
                }
                else if(i == R.id.profile){
                    frag = new Profile();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , frag).commit();
            }
        });

    }

}
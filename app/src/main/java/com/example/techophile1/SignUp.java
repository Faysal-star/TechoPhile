package com.example.techophile1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import android.view.animation.AnimationUtils;

public class SignUp extends AppCompatActivity {
    TextInputEditText dobTxt ;
    Animation top , right , bottom , left ;
    TextInputLayout userName,email,dob,pass,rpass;
    CircleImageView profilePic;
    Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setStatusBarColor(ContextCompat.getColor(this , R.color.startColor));

        // hooks
        dobTxt = findViewById(R.id.dobTxt);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        dob = findViewById(R.id.dob);
        pass = findViewById(R.id.pass);
        rpass = findViewById(R.id.rpass);
        profilePic = findViewById(R.id.profilePic);
        reg = findViewById(R.id.reg);

        // Animation
        top = AnimationUtils.loadAnimation(this , R.anim.top_animation);
        right = AnimationUtils.loadAnimation(this , R.anim.right_anim);
        bottom = AnimationUtils.loadAnimation(this , R.anim.bottom_animation);
        left = AnimationUtils.loadAnimation(this , R.anim.left_anim);

        profilePic.setAnimation(right);
        userName.setAnimation(left);
        email.setAnimation(left);
        dob.setAnimation(right);
        pass.setAnimation(left);
        rpass.setAnimation(right);
        reg.setAnimation(left);
        dobTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> md = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date of Birth")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                md.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM,yyyy" , Locale.getDefault());
                        String date = sdf.format(selection);
                        dobTxt.setText(date);
                    }
                });
                md.show(getSupportFragmentManager() , "Date Picker");
            }
        });
    }
}
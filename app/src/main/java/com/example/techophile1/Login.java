package com.example.techophile1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private TextInputLayout email , pass ;
    private Button loginBtn , regBtn;
    private Animation right , left , top;
    private TextView welcome , hola ;
    private ImageView logoBg;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(this , R.color.startColor));
        // Fade transition
//        Transition fade = new Fade();
//        fade.excludeTarget(android.R.id.statusBarBackground , true);
//        fade.excludeTarget(android.R.id.navigationBarBackground , true);
//        getWindow().setEnterTransition(fade);
//        getWindow().setExitTransition(fade);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        loginBtn = findViewById(R.id.login);
        regBtn = findViewById(R.id.reg);
        welcome = findViewById(R.id.welcome);
        hola = findViewById(R.id.hola);
        logoBg = findViewById(R.id.logoBg);

        // Animation
        right = AnimationUtils.loadAnimation(this , R.anim.right_anim);
        left = AnimationUtils.loadAnimation(this , R.anim.left_anim);
        top = AnimationUtils.loadAnimation(this , R.anim.top_animation);
        email.setAnimation(right);
        pass.setAnimation(left);
        loginBtn.setAnimation(right);
        regBtn.setAnimation(left);
        welcome.setAnimation(right);
        hola.setAnimation(left);
        logoBg.setAnimation(top);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Login.this, email.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                 String mail = email.getEditText().getText().toString();
                 String password = pass.getEditText().getText().toString();
                    if(mail.isEmpty() || password.isEmpty()) {
                        Toast.makeText(Login.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    }else{
                        mAuth.signInWithEmailAndPassword(mail , password).addOnCompleteListener(Login.this , task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this , MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this , SignUp.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View , String>(hola , "hola_trans");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this , pairs);
                startActivity(intent , options.toBundle());
            }
        });
    }
}
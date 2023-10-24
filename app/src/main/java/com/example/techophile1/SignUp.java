package com.example.techophile1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import android.view.animation.AnimationUtils;

public class SignUp extends AppCompatActivity {
    private TextInputEditText dobTxt ;
    private Animation top , right , bottom , left ;
    private TextInputLayout userName,email,dob,pass,rpass;
    private CircleImageView profilePic;
    private Button reg;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private FirebaseStorage storage;
    private Uri imageUri;
    private String imageUriStr;
    private CProgress cProgress;

    private String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setStatusBarColor(ContextCompat.getColor(this , R.color.startColor));

        cProgress = new CProgress(SignUp.this);
        // Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

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

        // Listeners
        dobTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(SignUp.this, "Profile Pic", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent , "Select Pic") , 45);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameStr = userName.getEditText().getText().toString();
                String emailStr = email.getEditText().getText().toString();
                String dobStr = dobTxt.getText().toString();
                String passStr = pass.getEditText().getText().toString();
                String rpassStr = rpass.getEditText().getText().toString();
//                String roleStr = "user";
//                String userIDStr = "null";
//                String lastMsgStr = "null";
//                String statusStr = "offline";
                if(userNameStr.isEmpty() || emailStr.isEmpty() || dobStr.isEmpty() || passStr.isEmpty() || rpassStr.isEmpty()){
                    Toast.makeText(SignUp.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else if(!emailStr.matches(emailRegex)){
                    email.setError("Please enter a valid email");
                    pass.setErrorEnabled(false);
                    rpass.setErrorEnabled(false);
                }else if(passStr.length() < 6){
                    pass.setError("Password must be at least 6 characters long");
                    email.setErrorEnabled(false);
                    rpass.setErrorEnabled(false);
                }else if(!passStr.equals(rpassStr)){
                    rpass.setError("Passwords do not match");
                    email.setErrorEnabled(false);
                    pass.setErrorEnabled(false);
                }else if(imageUri == null){
                    Toast.makeText(SignUp.this, "Please select a profile picture", Toast.LENGTH_SHORT).show();
                }else if(userNameStr.contains(" ")){
                    userName.setError("Username cannot contain spaces");
                    email.setErrorEnabled(false);
                    pass.setErrorEnabled(false);
                    rpass.setErrorEnabled(false);
                }else{
                    cProgress.show();
                    email.setErrorEnabled(false);
                    pass.setErrorEnabled(false);
                    rpass.setErrorEnabled(false);
                    mAuth.createUserWithEmailAndPassword(emailStr, passStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String userIDStr = task.getResult().getUser().getUid();
                                DatabaseReference root = db.getReference().child("Users").child(userIDStr);
                                StorageReference storageRef = storage.getReference().child("Pics").child(userIDStr);
                                storageRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if(task.isSuccessful()){
                                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    imageUriStr = uri.toString();
                                                    // User(String profilePic, String userName, String mail, String dob, String pass, String role, String userID, String lastMsg, String status)
                                                    User user = new User(imageUriStr , userNameStr , emailStr , dobStr , passStr , "0" , userIDStr , "null" , "offline");
                                                    root.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(SignUp.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(SignUp.this , Login.class);
                                                                cProgress.cancel();
                                                                startActivity(intent);
                                                                finish();
                                                            }else{
                                                                cProgress.cancel();
                                                                Toast.makeText(SignUp.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                        }else{
                                            cProgress.cancel();
                                            Toast.makeText(SignUp.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }else{
                                cProgress.cancel();
                                Toast.makeText(SignUp.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 45){
            if(data != null){
                imageUri = data.getData();
                profilePic.setImageURI(imageUri);
            }
        }
    }

    public void DatePicker() {
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
}
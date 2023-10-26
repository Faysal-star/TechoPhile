package com.example.techophile1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private TextView profileName,profileEmail,profileBday;
    private CircleImageView profilePic ;
    private TextInputLayout learnings , teachings ;
    private Button update, delete , signOut ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        profileBday = view.findViewById(R.id.profileBday);
        profilePic = view.findViewById(R.id.profilePic);
        learnings = view.findViewById(R.id.learnings);
        teachings = view.findViewById(R.id.teachings);
        update = view.findViewById(R.id.update);
        delete = view.findViewById(R.id.delete);
        signOut = view.findViewById(R.id.signOut);

        String userRef = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        database.getReference().child("Users").child(userRef).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                profileName.setText(user.getUserName());
                profileEmail.setText(user.getMail());
                profileBday.setText(user.getDob());
                learnings.getEditText().setText(user.getLearning());
                teachings.getEditText().setText(user.getTeaching());
                Picasso.get().load(user.getProfilePic()).into(profilePic);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String learning = learnings.getEditText().getText().toString();
                String teaching = teachings.getEditText().getText().toString();
                database.getReference().child("Users").child(userRef).child("learning").setValue(learning);
                database.getReference().child("Users").child(userRef).child("teaching").setValue(teaching);
                learnings.clearFocus();
                teachings.clearFocus();
                learnings.getEditText().setEnabled(false);
                teachings.getEditText().setEnabled(false);
                learnings.getEditText().setEnabled(true);
                teachings.getEditText().setEnabled(true);
//                Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Updated!!", Toast.LENGTH_SHORT).show();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(getActivity(),Login.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Signed Out!!", Toast.LENGTH_SHORT).show();
                requireActivity().finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference().child("Users").child(userRef).removeValue();
                FirebaseUser user = auth.getCurrentUser();
                StorageReference desertRef = storage.getReference().child("Pics/"+userRef);
                desertRef.delete();
                auth.signOut();
                user.delete();
                Intent intent = new Intent(getActivity(),Login.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Account Deleted!!", Toast.LENGTH_SHORT).show();
                requireActivity().finish();
            }
        });
        // Inflate the layout for this fragment
        return view ;
    }
}
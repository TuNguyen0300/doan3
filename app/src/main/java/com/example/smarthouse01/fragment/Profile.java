package com.example.smarthouse01.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthouse01.Login;
import com.example.smarthouse01.R;
import com.example.smarthouse01.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends Fragment {

    FirebaseUser user;
    DatabaseReference databaseReference;

    String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_profile, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView txtname = (TextView) view.findViewById(R.id.txt_name);
        final TextView txtemail = (TextView) view.findViewById(R.id.txt_email);
        final TextView txtpnumber = (TextView) view.findViewById(R.id.txt_pnumber);
        final TextView txtgreet = (TextView) view.findViewById(R.id.txt_greeting);

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    String fullname = userProfile.getFullname();
                    String email = userProfile.getEmail();
                    String pnumber = userProfile.getPnumber();

                    txtgreet.setText("Wellcome, " + fullname +"!");
                    txtname.setText(fullname);
                    txtemail.setText(email);
                    txtpnumber.setText(pnumber);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

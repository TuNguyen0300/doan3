package com.example.smarthouse01.fragment;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthouse01.R;
import com.example.smarthouse01.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Account extends Fragment {

    EditText edtacc, edtpass, edtfullname, edtpnumber;
    Button add, update;

    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_add_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //listView = (ListView) view.findViewById(R.id.list_account);

        edtacc =  (EditText) view.findViewById(R.id.add_name);
        edtpass =  (EditText) view.findViewById(R.id.add_pass);
        edtfullname =  (EditText) view.findViewById(R.id.add_fullname);
        edtpnumber =  (EditText) view.findViewById(R.id.add_pnumber);

        add =  (Button) view.findViewById(R.id.btn_add);

        mAuth = FirebaseAuth.getInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtacc.getText().toString();
                String password = edtpass.getText().toString();
                String fullname = edtfullname.getText().toString();
                String pnumber = edtpnumber.getText().toString();

                if(email.isEmpty()){
                    edtacc.setError("Account is required");
                    edtacc.requestFocus();
                    return;
                }
                if(fullname.isEmpty()){
                    edtfullname.setError("Fullname is required");
                    edtfullname.requestFocus();
                    return;
                }
                if(pnumber.isEmpty()){
                    edtpnumber.setError("Phone number is required");
                    edtpnumber.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edtacc.setError("Please enter a valid email");
                    edtacc.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    edtpass.setError("Password is required");
                    edtpass.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullname,email,password,pnumber);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Failed to add member", Toast.LENGTH_SHORT).show();                                            }
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }
}

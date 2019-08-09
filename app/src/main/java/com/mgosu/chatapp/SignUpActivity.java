package com.mgosu.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mgosu.chatapp.databinding.ActivitySignUpBinding;

import static com.mgosu.chatapp.FirebaseQuery.USERS;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding  binding;
    String firstName,LastName,UserName,Password;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = binding.edittextFirstname.getText().toString();
                LastName = binding.edittextLastName.getText().toString();
                UserName = binding.edittextUserName.getText().toString();
                Password = binding.edittextPassword.getText().toString();
                if (firstName.isEmpty() && LastName.isEmpty() && UserName.isEmpty() && Password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else {

                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference(USERS).child(UserName);

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                User user = new User();
                                user.firstname = firstName;
                                user.lastname = LastName;
                                user.password = Password;
                                user.username = UserName;

                                myRef.setValue(user, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        Toast.makeText(SignUpActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(SignUpActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });
    }
}

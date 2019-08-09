package com.mgosu.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mgosu.chatapp.databinding.ActivityMainBinding;

import static com.mgosu.chatapp.FirebaseQuery.USERS;

public class MainActivity extends AppCompatActivity  {
    private String TAG = " Main";
    DatabaseReference myRef;
    FirebaseDatabase database;
    ActivityMainBinding binding;
    String UserName,PassWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        binding.ButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserName = binding.edittextUserName.getText().toString();
                PassWord = binding.edittextPassword.getText().toString();

                database = FirebaseDatabase.getInstance();
                myRef = database.getReference(USERS).child(UserName);
                if (UserName.isEmpty() && PassWord.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                Toast.makeText(MainActivity.this, "Nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                            } else {
                                User user = dataSnapshot.getValue(User.class);
                                if (user.password.equals(PassWord) && user.username.equals(UserName)) {
                                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                } else {
                                    Toast.makeText(MainActivity.this, "lỗi", Toast.LENGTH_SHORT).show();
                                }
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

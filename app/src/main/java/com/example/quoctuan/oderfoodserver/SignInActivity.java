package com.example.quoctuan.oderfoodserver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.quoctuan.oderfoodserver.Common.Common;
import com.example.quoctuan.oderfoodserver.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import info.hoang8f.widget.FButton;

public class SignInActivity extends AppCompatActivity {

    MaterialEditText editPhone,editPass;
    FButton btnSignIn;

    FirebaseDatabase database;
    DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        FindViewID
        editPhone = (MaterialEditText) findViewById(R.id.editPhone);
        editPass = (MaterialEditText) findViewById(R.id.editPass);
        btnSignIn = (FButton) findViewById(R.id.btnSignIn);

//        Init FireBase
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

//        OnClick Button
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(editPhone.getText().toString(), editPass.getText().toString());
            }
        });
    }
//    Handle SignIn
    private void signInUser(final String phone, String pass) {
//       Send Message when wait too long
        final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
        mDialog.setMessage("Pleas Waiting");
        mDialog.show();
//      check Phone and Pass on Firebase
        final String localPhone = phone;
        final String localPass = pass;
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(localPhone).exists()){ //Phone Exist in Firebase
                    mDialog.dismiss();
//                    Get Phone Number
                    Users user = dataSnapshot.child(localPhone).getValue(Users.class);
                    user.setPhone(localPhone);

//                    If IsStaff == true
                    if (Boolean.parseBoolean(user.getIsStaff())){
                        if (user.getPassword().equals(localPass)){
//                            Login Ok
                            Intent intent = new Intent(SignInActivity.this,HomeActivity.class);
                            Common.currentUser = user;
                            startActivity(intent);
                            finish();

                        }else {
                            Toast.makeText(SignInActivity.this, "Wrong PassWord !", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SignInActivity.this, "Please Login With Staff Account", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    mDialog.dismiss();
                    Toast.makeText(SignInActivity.this, "User not Exist !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

package com.example.complaintapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    //views
    EditText mEmailEt, mPasswordEt,mconPasswordEt,mNicEt;
    Button mRegisterBtn;
    TextView mHaveaccount;

    //Progress bar to display while registering
    ProgressDialog progressDialog;

    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //init
        mEmailEt=findViewById(R.id.emailEt);
        mPasswordEt=findViewById(R.id.passwordEt);
        mRegisterBtn=findViewById(R.id.register_Btn);
        mHaveaccount=findViewById(R.id.have_acc);
        mNicEt=findViewById(R.id.nicEt);
        mconPasswordEt=findViewById(R.id.conpasswordEt);

        //In the onCreate() method, initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");

        //handle register button click
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input email, password
                String nic=mNicEt.getText().toString().trim();
                String email=mEmailEt.getText().toString().trim();
                String password=mPasswordEt.getText().toString().trim();
                String conPassword=mconPasswordEt.getText().toString().trim();
                //validate
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //set error and forcus to email edittext
                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);
                }
                else if(password.length()<6){
                    mPasswordEt.setError("Password at least 6 characters");
                    mEmailEt.setFocusable(true);
                }
                else if(!password.equals(conPassword)){
                    mconPasswordEt.setError("Password doesn't match");
                    mEmailEt.setFocusable(true);
                }
                else if(nic.length()!=12){
                    mNicEt.setError("NIC doesn't match");
                    mNicEt.setFocusable(true);
                }
                else{
                   registerUser(email, password,nic); //register the user
                }

            }
        });

        mHaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

    private void registerUser(String email, String password, final String NIC) {
        //email password is valid, show progress dialog and register user

        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, dismiss dialog and start registering user
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            //Get user email and uid from auth
                            String email=user.getEmail();
                            String uid=user.getUid();
                            //when user is registered storeuser info in firebase realtime database too
                            //using Hashmap

                            HashMap<Object, String> hashmap=new HashMap<>();
                            //put information in hashmap
                            hashmap.put("email",email);
                            hashmap.put("uid",uid);
                            hashmap.put("name","");
                            hashmap.put("phone","");
                            hashmap.put("Address","");
                            hashmap.put("NIC",NIC);
                            hashmap.put("image","");
                            // hashmap.put("cover","");
                            //firebase database instance

                            FirebaseDatabase database=FirebaseDatabase.getInstance();

                            //path to store user data named "Users"
                            DatabaseReference reference=database.getReference("Users");
                            //put data within hashmap in database
                            reference.child(uid).setValue(hashmap);

                            Toast.makeText(RegisterActivity.this, "Registered...\n"+user.getEmail(),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, DrawerActivity.class));
                            finish();

                        } else {
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //error, dismiss the progress dialog and get and show the error message
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this,"Authentication Failed.",Toast.LENGTH_SHORT).show();
            }
        });


    }

}

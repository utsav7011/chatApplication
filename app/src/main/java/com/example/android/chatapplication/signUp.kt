package com.example.android.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signUp : AppCompatActivity() {

    private lateinit var edtName:EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mDbRef: DatabaseReference

    //    initialize the firebase authentication :
    private lateinit var mAuth: FirebaseAuth;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        edtName = findViewById(R.id.edtName)
        edtEmail = findViewById(R.id.edtEmail)
        edtpassword = findViewById(R.id.edtPassword)
        btnSignUp = findViewById(R.id.buttonSignup)

        // initialize the firebase authentication:

        mAuth = FirebaseAuth.getInstance();

        // check for the login authentication ;
        btnSignUp.setOnClickListener {
            val name = edtName.text.toString();
            val email = edtEmail.text.toString();
            val password = edtpassword.text.toString();

            // method for signup:
            signUp(name,email, password)
        }
    }

    private fun signUp(name:String, email:String , password:String){
//        logic for signup user :

        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task->
                if (task.isSuccessful){
                    // code for jumping to home activity :
                    addUserToDatabase(name,email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Error Occurred ", Toast.LENGTH_SHORT).show()
                    Log.e("error123", "signup error failure L ", task.exception)
                }
            }
    }

    private fun addUserToDatabase(name:String , email:String, uid:String){

        mDbRef = FirebaseDatabase.getInstance().getReference()
        // child add a node to the database :

        mDbRef.child("user").child(uid).setValue(user(name,email,uid))

    }


    // onCreateActivityEnds :
}
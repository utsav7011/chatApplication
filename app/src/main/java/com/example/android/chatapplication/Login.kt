package com.example.android.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edtEmail:EditText
    private lateinit var edtpassword:EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button

//    declare the firebase authentication :
    private lateinit var mAuth:FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        hide the action bar:
        supportActionBar?.hide()

        edtEmail = findViewById(R.id.edtEmail)
        edtpassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.buttonLogin)
        btnSignUp = findViewById(R.id.buttonSignup)


        btnSignUp.setOnClickListener {
            val intent = Intent(this, signUp::class.java)
            finish()
            startActivity(intent);
        }

        // initialize the firebase authentication:

        mAuth = FirebaseAuth.getInstance();

        // check for the login authentication ;
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtpassword.text.toString();
            // method for login :
            login(email, password)
        }
    }


    private fun login(email:String , password:String){
//        logic for user login:

        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task->
                if (task.isSuccessful){
                    // code for logging in user :
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "User Does not exists : ",Toast.LENGTH_SHORT).show()
                    Log.e("error123", "signup error failure L ", task.exception)
                }
            }

    }



}
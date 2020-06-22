package com.ucek.csic

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ucek.csic.model.UserData
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

//    private lateinit var nameSignup: EditText
//    private lateinit var emailSignup: EditText
//    private lateinit var passwordSignup: EditText
//    private lateinit var confirmPasswordSignup: EditText
//    private lateinit var btnSignup: Button

    private var rlayout: RelativeLayout? = null
    private var animation: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val toolbar = findViewById<Toolbar>(R.id.bgHeader)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        rlayout = findViewById(R.id.rlayout)
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal)
        rlayout?.setAnimation(animation)

//        nameSignup = findViewById(R.id.nameSignup)
//        emailSignup = findViewById(R.id.emailSignup)
//        passwordSignup = findViewById(R.id.passwordSignup)
//        confirmPasswordSignup = findViewById(R.id.confirmPasswordSignup)
//        btnSignup = findViewById(R.id.btnSignup)

        btnSignup.setOnClickListener {
            signupUser()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }

//    private fun updateUI(currentUser: FirebaseUser?) {
//        if (currentUser != null)
//            if (currentUser.isEmailVerified) {
//                database = Firebase.database.reference
//                database.child("users").child(currentUser!!.uid).child("Verification").setValue("YES")
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//
//            } else {
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            }
//    }

    private fun signupUser() {
        if (nameSignup.text.isEmpty()) {
            nameSignup.error = "Please enter your name"
            nameSignup.requestFocus()
            return
        }
        if (emailSignup.text.isEmpty()) {
            emailSignup.error = "Please enter an email"
            emailSignup.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailSignup.text.toString()).matches()) {
            emailSignup.error = "Please enter a valid email"
            emailSignup.requestFocus()
            return
        }
        if (passwordSignup.text.isEmpty()) {
            passwordSignup.error = "Please enter a password"
            passwordSignup.requestFocus()
            return
        }
        if (!isValidPassword(passwordSignup.text.toString())) {
            passwordSignup.error = "Password must be 8-16 characters long and should contain an uppercase and a lowercase letter   "
            passwordSignup.requestFocus()
            return
        }
        if (confirmPasswordSignup.text.isEmpty()) {
            confirmPasswordSignup.error = "Please confirm your password"
            confirmPasswordSignup.requestFocus()
            return
        }
        if (confirmPasswordSignup.text.toString() != passwordSignup.text.toString()) {
            confirmPasswordSignup.setText("")
            confirmPasswordSignup.error = "Passwords doesn't match"
            confirmPasswordSignup.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(emailSignup.text.toString(), passwordSignup.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser
                        getUSerData(user)
                        user!!.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d("Register Activity", "Email sent.")
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
                                    }
                                }
//                        Toast.makeText(baseContext, "Login to continue.", Toast.LENGTH_LONG).show()
//                        startActivity(Intent(this, MainActivity::class.java))
//                        finish()
                    } else {
                        Toast.makeText(baseContext, "Signup failed",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z]).{8,16}\$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun getUSerData(userobject: FirebaseUser?) {
        val user = UserData(
                nameSignup.text.toString(),
                emailSignup.text.toString(),
                "NO"
        )
        database = Firebase.database.reference
        database.child("users").child(userobject!!.uid).setValue(user)
    }
}
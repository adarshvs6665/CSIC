package com.ucek.csic

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ucek.csic.R
import com.ucek.csic.model.UserData

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameSignup: EditText
    private lateinit var emailSignup: EditText
    private lateinit var passwordSignup: EditText
    private lateinit var confirmPasswordSignup: EditText
    private lateinit var btnSignup: Button

    private var rlayout: RelativeLayout? = null
    private var animation: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val toolbar = findViewById<Toolbar>(R.id.bgHeader)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        rlayout = findViewById(R.id.rlayout)
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal)
        rlayout?.setAnimation(animation)

        nameSignup = findViewById(R.id.nameSignup)
        emailSignup = findViewById(R.id.emailSignup)
        passwordSignup = findViewById(R.id.passwordSignup)
        confirmPasswordSignup = findViewById(R.id.confirmPasswordSignup)
        btnSignup = findViewById(R.id.btnSignup)

        btnSignup.setOnClickListener {
            Toast.makeText(baseContext,"Onclick working", Toast.LENGTH_LONG).show()
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
}
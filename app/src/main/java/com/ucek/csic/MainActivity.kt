package com.ucek.csic

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.util.Pair
import android.util.Patterns
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private var btRegister: ImageButton? = null
    private var tvLogin: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btRegister = findViewById(R.id.btRegister)
        tvLogin = findViewById(R.id.tvLogin)
        btRegister?.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener() {
            loginUser()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(v: View) {
        if (v === btRegister) {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            val pairs: Array<Pair<*, *>?> = arrayOfNulls(1)
            pairs[0] = Pair<View?, String>(tvLogin, "tvLogin")
            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this@MainActivity, pairs[0] as Pair<View, String>?)
            startActivity(intent, activityOptions.toBundle())
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                database = Firebase.database.reference
                database.child("users").child(currentUser!!.uid).child("verification").setValue("YES")
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(baseContext,"Please verify your email address", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser() {
        if (emailLogin.text.isEmpty()) {
            emailLogin.error = "Please enter an email"
            emailLogin.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailLogin.text.toString()).matches()) {
            emailLogin.error = "Please enter a valid email"
            emailLogin.requestFocus()
            return
        }
        if (passwordLogin.text.isEmpty()) {
            passwordLogin.error = "Please enter a password"
            passwordLogin.requestFocus()
            return
        }
        if (!isValidPassword(passwordLogin.text.toString())) {
            passwordLogin.error = "Password must be 8-16 characters long and should contain an uppercase and a lowercase letter   "
            passwordLogin.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(emailLogin.text.toString(), passwordLogin.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
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
}
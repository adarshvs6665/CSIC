package com.ucek.csic

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.util.Pair
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ucek.csic.RegisterActivity
import com.ucek.csic.R

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailLogin: EditText
    private lateinit var passwordLogin: EditText

    private var btRegister: ImageButton? = null
    private var tvLogin: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        btRegister = findViewById(R.id.btRegister)
        tvLogin = findViewById(R.id.tvLogin)
        btRegister?.setOnClickListener(this)

        emailLogin = findViewById(R.id.emailLogin)
        passwordLogin = findViewById(R.id.passwordLogin)
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

    }
}
package com.ucek.csic

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Pair
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.ucek.csic.RegisterActivity
import com.ucek.csic.R

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var btRegister: ImageButton? = null
    private var tvLogin: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btRegister = findViewById(R.id.btRegister)
        tvLogin = findViewById(R.id.tvLogin)
        btRegister?.setOnClickListener(this)
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
}
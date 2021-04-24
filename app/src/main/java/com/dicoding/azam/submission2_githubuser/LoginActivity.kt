package com.dicoding.azam.submission2_githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.title = resources.getString(R.string.login)

        val tv_username = findViewById(R.id.tv_username) as EditText
        val tv_password = findViewById(R.id.tv_password) as EditText
        val btn_login = findViewById(R.id.btn_login) as Button
        val btn_reset = findViewById(R.id.btn_reset) as Button

        btn_reset.setOnClickListener {
            tv_username.setText("")
            tv_password.setText("")
        }

        btn_login.setOnClickListener {
            if (tv_username.text.trim().isNotEmpty() || tv_password.text.trim().isNotEmpty()) {
                Toast.makeText(this, tv_username.text, Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, R.string.pesan , Toast.LENGTH_LONG).show()
            }
        }
    }
}
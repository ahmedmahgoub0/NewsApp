package com.example.newsapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.newsapp.databinding.ActivitySplashBinding
import com.example.newsapp.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        showSplash()
    }

    private fun showSplash() {
        Handler(Looper.getMainLooper())
            .postDelayed( { val intent =
            Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }


}
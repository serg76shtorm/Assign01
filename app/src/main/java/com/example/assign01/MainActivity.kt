package com.example.assign01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.assign01.databinding.ActivityMainBinding
import com.example.assign01.util.AppPreferences
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i(
            "MainActivityMain", "это onCreate() called ".plus(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy: HH.mm.ss.SSS"))
            )
        )

        intentParsing()
    }

    fun intentParsing() {
        val email = intent.getStringExtra(AppPreferences.APP_SETTING_EMAIL)
        val charD = email.toString().indexOf('@', 0)
        val name = email.toString().substring(0, charD)

        binding.textView.text = name
        binding.textView3.text = email
    }

    fun onClickTextView(view: View) {
        val intent = Intent(this, SettingActivity::class.java)

        startActivity(intent)
        overridePendingTransition(R.anim.diagonaltranslate, R.anim.alpha)
    }

    override fun onStart() {
        super.onStart()
        val sdf = SimpleDateFormat("yyyy-MM-dd:HH.mm.ss.SSS", Locale.getDefault())
        Log.i(
            "MainActivityMain",
            "это onStart() called ".plus(sdf.format(Calendar.getInstance().time))
        )
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(
            "MainActivityMain", "onRestart() called ".plus(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy: HH.mm.ss.SSS"))
            )
        )
        recreate()
    }

    override fun onResume() {
        super.onResume()
        Log.i(
            "MainActivityMain", "onRestart() called ".plus(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy: HH.mm.ss.SSS"))
            )
        )
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivityMain", "onPause() called ".plus(LocalDateTime.now()))
    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivityMain", "onStop() called ".plus(LocalDateTime.now()))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivityMain", "onDestroy() called ".plus(LocalDateTime.now()))
    }

}
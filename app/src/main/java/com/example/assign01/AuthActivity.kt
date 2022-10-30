package com.example.assign01

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import androidx.core.view.isVisible
import android.content.SharedPreferences
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.assign01.databinding.ActivityAuthBinding
import com.example.assign01.util.AppPreferences
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var preferences: SharedPreferences
    val appPref = AppPreferences()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i(
            "MainActivityAuth", "это onCreate() called ".plus(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy: HH.mm.ss.SSS"))
            )
        )

        binding.editTextTextEmailAddress.setOnFocusChangeListener(object :
            View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if ((hasFocus) && (binding.textInputEditText2.isVisible)) {
                    binding.textInputEditText2.visibility = View.INVISIBLE
                } else if ((!hasFocus) && (binding.editTextTextEmailAddress.text.toString().length > 0)) {
                    emailTest()
                }
            }
        })

        binding.editTextTextPassword.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if ((hasFocus) && (binding.textInputEditText.isVisible)) {
                    binding.textInputEditText.visibility = View.INVISIBLE
                } else if ((!hasFocus) && (binding.editTextTextPassword.text.toString().length >= 0)) {
                    passwordTest()
                }
            }
        })

        getPreferences()
    }

    fun getPreferences() {
        val mode: Int
        val theme: Boolean
        val lang = LocaleManager().getLanguage(this)
        val email: String

        if (lang != Locale.getDefault().language) {
            LocaleManager().setLocale(this, lang)
            recreate()
        }

        preferences = getSharedPreferences(appPref.APP_SETTING, Context.MODE_PRIVATE)
        if (preferences.contains(appPref.APP_SETTING_THEME)) {
            theme = preferences.getBoolean(appPref.APP_SETTING_THEME, false)

            mode = AppCompatDelegate.getDefaultNightMode()
            if ((mode == -100) && (theme)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else if ((mode == -100) && (!theme)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        if (preferences.contains(appPref.APP_SETTING_EMAIL)) {
            email = preferences.getString(appPref.APP_SETTING_EMAIL, "").toString()
            binding.editTextTextEmailAddress.text =
                Editable.Factory.getInstance().newEditable(email)
        }
    }

    fun emailTest(): Boolean {
        val charD = binding.editTextTextEmailAddress.text.toString().indexOf('@', 0)
        if ((charD > 0) && (binding.editTextTextEmailAddress.text.toString()
                .indexOf('.', charD) >= 0)
        ) {
            binding.textInputEditText2.visibility = View.INVISIBLE
            return true
        } else {
            binding.textInputEditText2.visibility = View.VISIBLE
        }
        return false
    }

    fun passwordTest(): Boolean {
        if (binding.editTextTextPassword.text.toString() == "123") {
            binding.textInputEditText.visibility = View.INVISIBLE
            return true
        } else {
            binding.textInputEditText.visibility = View.VISIBLE
        }
        return false
    }

    fun onClickButton(view: View) {
        if ((emailTest()) && (passwordTest())) {
            val email = binding.editTextTextEmailAddress.text.toString()

            if (binding.checkBox.isChecked) {
                preferences = getSharedPreferences(appPref.APP_SETTING, Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putString(appPref.APP_SETTING_EMAIL, email).apply()
//                editor.clear().apply()
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(appPref.APP_SETTING_EMAIL, email)

            startActivity(intent)
            overridePendingTransition(R.anim.rotate, R.anim.alpha)
        }
    }

    override fun onStart() {
        super.onStart()
        val sdf = SimpleDateFormat("yyyy-MM-dd:HH.mm.ss.SSS", Locale.getDefault())
        Log.i(
            "MainActivityAuth",
            "это onStart() called ".plus(sdf.format(Calendar.getInstance().time))
        )
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(
            "MainActivityAuth", "onRestart() called ".plus(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy: HH.mm.ss.SSS"))
            )
        )
    }

    override fun onResume() {
        super.onResume()
        Log.i(
            "MainActivityAuth", "onRestart() called ".plus(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy: HH.mm.ss.SSS"))
            )
        )
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivityAuth", "onPause() called ".plus(LocalDateTime.now()))
    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivityAuth", "onStop() called ".plus(LocalDateTime.now()))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivityAuth", "onDestroy() called ".plus(LocalDateTime.now()))
    }

}


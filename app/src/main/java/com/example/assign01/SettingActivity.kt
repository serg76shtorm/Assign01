package com.example.assign01

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.example.assign01.util.AppPreferences
import kotlinx.android.synthetic.main.activity_sett.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SettingActivity : AppCompatActivity() {
    private lateinit var preferences: SharedPreferences
    private lateinit var switchChecked: SwitchCompat //не работает
    private lateinit var radioGroup: RadioGroup //не работает

    val appPref= AppPreferences()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sett)

        Log.i("MainActivitySett", "это onCreate() called ".plus(LocalDateTime.now().
            format(DateTimeFormatter.ofPattern("dd.MM.yyyy: HH.mm.ss.SSS"))))

        getPreferences()
    }

    fun getPreferences(){
        val theme:Boolean
        val lang:Int

        preferences = getSharedPreferences(appPref.APP_SETTING, Context.MODE_PRIVATE)
        if (preferences.contains(appPref.APP_SETTING_THEME)){
            theme=preferences.getBoolean(appPref.APP_SETTING_THEME, false)
            switch1.isChecked=theme
        }

        if (preferences.contains(appPref.APP_SETTING_LANG)){
            lang=preferences.getInt(appPref.APP_SETTING_LANG, 0)
            radio_group.check(radio_group.getChildAt(lang).id)
        }
    }

    override fun attachBaseContext(base: Context) {
        LocaleManager().setLocale(base, LocaleManager().getLanguage(base))
        super.attachBaseContext(LocaleManager().onAttach(base))
    }

    fun onClickRadioButton(view: View){
        radioGroupChecked(view)
    }

    fun radioGroupChecked(check: View){
        val index:Int =radio_group.indexOfChild(check)
        when(index){
            1 -> LocaleManager().setLocale(this, "ru")
            2 -> LocaleManager().setLocale(this, "uk")
            else -> LocaleManager().setLocale(this, "en")
        }

        recreate()

        preferences = getSharedPreferences(appPref.APP_SETTING, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt(appPref.APP_SETTING_LANG, index).apply()
    }

    fun onClickSwitch(view: View)
    {
        if(switch1.isChecked){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        preferences = getSharedPreferences(appPref.APP_SETTING, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean(appPref.APP_SETTING_THEME, switch1.isChecked).apply()

//        val toast=Toast.makeText(this, "Зачем вы нажали?", Toast.LENGTH_SHORT).apply { setGravity(Gravity.TOP , 0, 0); show()}
/*        toast.setGravity(Gravity.TOP or Gravity.RIGHT , 0, 0)//не работает
        val toastContainer = toast.view as LinearLayout
        val switchImage = ImageView(this)
        switchImage.setImageResource(R.drawable.user)
        toastContainer.addView(switchImage, 0)
        toast.show()*/
    }

    fun onClickImageButton(view: View)
    {
        finish()
        overridePendingTransition(R.anim.activity_down_up_close_enter, R.anim.activity_down_up_close_exit)
    }

    override fun onStart() {
        super.onStart()
        val sdf = SimpleDateFormat("yyyy-MM-dd:HH.mm.ss.SSS", Locale.getDefault())
        Log.i("MainActivitySett", "это onStart() called ".plus(sdf.format(Calendar.getInstance().time)))
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MainActivitySett", "onRestart() called ".plus(LocalDateTime.now().
            format(DateTimeFormatter.ofPattern("dd.MM.yyyy: HH.mm.ss.SSS"))))
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainActivitySett", "onRestart() called ".plus(LocalDateTime.now().
            format(DateTimeFormatter.ofPattern("dd.MM.yyyy: HH.mm.ss.SSS"))))
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivitySett", "onPause() called ".plus(LocalDateTime.now()))
    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivitySett", "onStop() called ".plus(LocalDateTime.now()))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivitySett", "onDestroy() called ".plus(LocalDateTime.now()))
    }
}
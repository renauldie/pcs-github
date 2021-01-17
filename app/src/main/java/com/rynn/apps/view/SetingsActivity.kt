package com.rynn.apps.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rynn.apps.R
import kotlinx.android.synthetic.main.activity_set.*

class SetingsActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val PREFS_NAME = "setting_pref"
        private const val DAILY = "daily"
    }

    private lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)

        mSharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (supportActionBar != null) {
            supportActionBar?.title = getString(R.string.title_setting)
        }

        set_language.setOnClickListener(this)

    }

    private fun saveChange(value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(DAILY, value)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.set_language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
        }
    }
}
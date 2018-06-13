@file:Suppress("PrivatePropertyName")

package com.example.nikhil.ssoserver

import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var filter = IntentFilter()
    private var receiver = SSOReceiver()
    companion object {
        const val KEY_LOGIN = "loginStatus"
        const val KEY_USERNAME = "username"
        const val KEY_PASSWORD = "password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filter.addAction(SSOReceiver.SSOLogin_ACTION)
        filter.addAction(SSOReceiver.SSOStatus_ACTION)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val loggedIn = preferences.getBoolean(KEY_LOGIN, false)
        if (!loggedIn) {
            loginFalse.visibility = View.VISIBLE
            loginTrue.visibility = View.GONE
            loginButton.setOnClickListener {
                val uname = username.text.toString()
                val pwd = password.text.toString()
                val editor = preferences.edit()
                editor.putBoolean(KEY_LOGIN, true)
                editor.putString(KEY_USERNAME, uname)
                editor.putString(KEY_PASSWORD, pwd)
                editor.apply()
                finish()
            }
        } else {
            loginFalse.visibility = View.GONE
            loginTrue.visibility = View.VISIBLE
            val uname = preferences.getString(KEY_USERNAME, "None")
            loginTrue.text = "You are already logged in as $uname"

        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.logoutMenu) {
            logoutUser()
        }
        return true
    }

    private fun logoutUser() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        editor.remove(KEY_LOGIN)
        editor.remove(KEY_USERNAME)
        editor.remove(KEY_PASSWORD)
        editor.apply()
    }

}

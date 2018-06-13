package com.example.nikhil.ssoserver

import android.app.IntentService
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log

class SSOService: IntentService("SSOService") {

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            if (intent.action == SSOReceiver.SSOLogin_ACTION) {
                val loginIntent = Intent(applicationContext, MainActivity::class.java)
                loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                Log.d("IntentService", "logging in on server")
                startActivity(loginIntent)
            } else if (intent.action == SSOReceiver.SSOStatus_ACTION) {
                val prefs = PreferenceManager.getDefaultSharedPreferences(this)
                val loggedIn = prefs.getBoolean(MainActivity.KEY_LOGIN, false)
                val statusIntent = Intent()
                Log.d("IntentService", "Checking login status on server")
                statusIntent.action = "android.intent.action.CLIENT_LOGIN_STATUS"
                statusIntent.putExtra(MainActivity.KEY_LOGIN, loggedIn)
                if (loggedIn) {
                    val uname = prefs.getString(MainActivity.KEY_USERNAME, "None")
                    statusIntent.putExtra(MainActivity.KEY_USERNAME, uname)
                }
                sendBroadcast(statusIntent)
            }
        }
    }

}
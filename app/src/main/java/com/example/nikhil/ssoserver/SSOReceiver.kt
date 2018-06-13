@file:Suppress("MayBeConstant")

package com.example.nikhil.ssoserver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SSOReceiver: BroadcastReceiver() {

    companion object {
        val SSOLogin_ACTION = "android.intent.action.SSOLogin"
        val SSOStatus_ACTION = "android.intent.action.LoginStatus"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && context != null) {
            val action = intent.action
            if (action == SSOLogin_ACTION) {
                val loginIntent = Intent(context, SSOService::class.java)
                Log.d("SSOReceiver", "Login broadcast received")
                loginIntent.action = SSOLogin_ACTION
                context.startService(loginIntent)
            } else if (action == SSOStatus_ACTION) {
                val statusIntent = Intent(context, SSOService::class.java)
                Log.d("SSOReceiver", "Status broadcast received!")
                statusIntent.action = SSOStatus_ACTION
                context.startService(statusIntent)
            }
        }
    }

}
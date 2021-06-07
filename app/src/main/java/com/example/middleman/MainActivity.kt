package com.example.middleman

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.middleman.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataName: String
    private lateinit var dataId: String
    private lateinit var dataEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentFilter = IntentFilter("android.example.myBroadcastMessage")
        registerReceiver(myReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myReceiver)
    }

    private val myReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if ("android.example.myBroadcastMessage" == intent!!.action) {
                dataName = intent.getStringExtra("android.example.name")!!
                dataId = intent.getStringExtra("android.example.id")!!
                dataEmail = intent.getStringExtra("android.example.email")!!

                Toast.makeText(context, "MiddleMan App received Data", Toast.LENGTH_SHORT).show()
                sendDataToReceiverApp()
            }
        }

    }

    private fun sendDataToReceiverApp() {

        val intent = Intent("android.example.Receiver.myBroadcast").apply {
            putExtra("android.example.name", dataName)
            putExtra("android.example.id", dataId)
            putExtra("android.example.email", dataEmail)
            flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        }
        sendBroadcast(intent)
    }


}
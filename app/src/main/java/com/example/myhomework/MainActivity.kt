package com.example.myhomework

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myhomework.ui.Music.MusicFragment
import com.example.myhomework.ui.game.gameFragment
import com.example.myhomework.ui.watch.WatchFragment
import com.example.myhomework.ui.weather.WeatherFragment

class MainActivity : AppCompatActivity() {
    val fragment1 = MusicFragment()
    val fragment2 = gameFragment()
    val fragment3 = WeatherFragment()
    val fragment4 = WatchFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment1)
                .commit()
        }
        val bottomNV = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNV.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_music -> supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment,fragment1)
                    .commit()
                R.id.navigation_game-> supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment,fragment2)
                    .commit()
                R.id.navigation_weather -> supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment,fragment3)
                    .commit()
                R.id.navigation_watch -> supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment,fragment4)
                    .commit()
            }
            true
        }
    }
    fun printAllFragments() {
        supportFragmentManager.fragments.forEach {
            Log.d("Fragment",it.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(myReceiver)
    }

}
class MyReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("Receiver", "receive message")
    }
}
package com.jacobarau.ptzbetter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        val model: CameraModel by viewModels()
        preset1.setOnClickListener { model.goToPreset(1) }
        preset2.setOnClickListener { model.goToPreset(2) }
        preset3.setOnClickListener { model.goToPreset(3) }
        preset4.setOnClickListener { model.goToPreset(4) }
        preset5.setOnClickListener { model.goToPreset(5) }
        preset6.setOnClickListener { model.goToPreset(6) }
        preset7.setOnClickListener { model.goToPreset(7) }
        preset8.setOnClickListener { model.goToPreset(8) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.set_camera_ip_menu_item -> {
                // show the set ip dialog
                SetIPDialog().show(supportFragmentManager, "somethingGoesHere")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
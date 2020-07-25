package com.jacobarau.ptzbetter

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager

class CameraModel(application: Application) : AndroidViewModel(application) {
    private val camIPSettingString = "CameraIPSetting"
    private val propertyListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        Log.i("blah", "property changed $key")
        if (key == camIPSettingString) {
            cameraIP.value = sharedPreferences.getString(camIPSettingString, null)
            Log.i("blah", cameraIP.value)
        }
    }

    private val cameraIP: MutableLiveData<String> by lazy {
        PreferenceManager.getDefaultSharedPreferences(getApplication())
            .registerOnSharedPreferenceChangeListener(propertyListener)
        val data = MutableLiveData<String>()
        data.value = PreferenceManager.getDefaultSharedPreferences(getApplication()).getString(camIPSettingString, null)
        data
    }

    fun getCameraIP(): LiveData<String> {
        return cameraIP
    }

    fun setCameraIP(newIP: String) {
        PreferenceManager.getDefaultSharedPreferences(getApplication()).edit().putString(camIPSettingString, newIP).apply()
    }
}
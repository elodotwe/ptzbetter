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
    private val propertyListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == camIPSettingString) {
            cameraIP.value = currentCameraIP()
        }
    }

    private val cameraIP: MutableLiveData<String> by lazy {
        PreferenceManager.getDefaultSharedPreferences(getApplication())
            .registerOnSharedPreferenceChangeListener(propertyListener)
        val data = MutableLiveData<String>()
        data.value = currentCameraIP()
        data
    }

    private fun currentCameraIP(): String? {
        return PreferenceManager.getDefaultSharedPreferences(getApplication()).getString(camIPSettingString, null)
    }

    fun getCameraIP(): LiveData<String> {
        return cameraIP
    }

    fun setCameraIP(newIP: String) {
        PreferenceManager.getDefaultSharedPreferences(getApplication()).edit().putString(camIPSettingString, newIP).apply()
    }

    fun goToPreset(preset: Int) {
        Log.i("blah", "going to preset $preset")
    }
}
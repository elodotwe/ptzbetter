package com.jacobarau.ptzbetter

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import androidx.preference.PreferenceManager

class CameraModel(application: Application) : AndroidViewModel(application), LifecycleObserver {
    private val camIPSettingString = "CameraIPSetting"
    private val propertyListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == camIPSettingString) {
            cameraIP.value = currentCameraIP()
        }
    }

    private val cameraIP: MutableLiveData<String> by lazy {
        preferences().registerOnSharedPreferenceChangeListener(propertyListener)
        val data = MutableLiveData<String>()
        data.value = currentCameraIP()
        data
    }

    private fun preferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(getApplication())
    }

    private fun currentCameraIP(): String? {
        return preferences().getString(camIPSettingString, null)
    }

    fun getCameraIP(): LiveData<String> {
        return cameraIP
    }

    fun setCameraIP(newIP: String) {
        preferences().edit().putString(camIPSettingString, newIP).apply()
    }

    fun goToPreset(preset: Int) {
        Log.i("blah", "going to preset $preset")
    }

    private val _cameraStatus: MutableLiveData<CameraStatus> by lazy {
        MutableLiveData<CameraStatus>().also { it.value = CameraStatus.offline }
    }

    val cameraStatus:LiveData<CameraStatus> = _cameraStatus

    // TODO: these aren't exactly what we want, as Android onStop()s the activity on rotation.
    // but it's good enough for a MVP.
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onResume() {
        Log.i("blah", "started")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onPause() {
        Log.i("blah", "stopped")
    }
}
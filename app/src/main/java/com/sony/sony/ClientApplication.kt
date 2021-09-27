package com.sony.sony

import android.app.Application

class ClientApplication : Application() {

    companion object {
        var mapAppLocalization: HashMap<String, String>? = null
    }

    override fun onCreate() {
        super.onCreate()
        initLocalization()
    }

    /**
     * bind the local string files data into map, so further, localization can be refereed easily
     */
    private fun initLocalization() {
        val loc = resources.getStringArray(R.array.localization)
        val locPosition = resources.getStringArray(R.array.localization_key)
        mapAppLocalization = HashMap<String, String>(locPosition.size)
        for (i in locPosition.indices) {
            mapAppLocalization?.put(locPosition[i], loc[i])
        }
    }
}
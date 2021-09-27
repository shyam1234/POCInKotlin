package com.sony.sony

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sony.sony.ClientApplication.Companion.mapAppLocalization


/**
 * used view model for observing the changes
 */
class MainViewModel : ViewModel() {
    private val message: MutableLiveData<HashMap<String, String>> = MediatorLiveData()

    fun updateMessage(): MutableLiveData<HashMap<String, String>> {
        return message
    }

    fun updateMessage(map: HashMap<String, String>) {
        for ((key, value) in mapAppLocalization !!) {
            if (!map.containsKey(key)) {
                map[key] = value
            }
        }
        message.postValue(map)
    }
}
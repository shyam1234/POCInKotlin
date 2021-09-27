package com.sony.sony

import android.util.Log
import okhttp3.*
import java.io.IOException

/**
 * this class having external localization API handling logic
 */
class APIManager private constructor() {
    private val mClient: OkHttpClient

    enum class API(var url: String) {
        LOCALIZATION("https://run.mocky.io/v3/269ff09c-e0e0-48f8-bd69-8f76533e4155");
    }

    companion object {
        private var mInstance: APIManager? = null
        val instance: APIManager
            get() {
                if (mInstance == null) {
                    mInstance = APIManager()
                }
                return mInstance as APIManager
            }
    }

    init {
        mInstance = this
        mClient = OkHttpClient()
    }

    interface OnUpdateListener {
        fun updateView(responseString: String?)
    }

    fun getLocalization(api: API, onUpdateViewListener: OnUpdateListener) {
        val request: Request = Request.Builder()
            .url(api.url)
            .build()
        mClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onUpdateViewListener.updateView(e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                } else {
                    Log.i("Response:", response.toString())
                    Log.i("Response body:", response.body.toString())
                    Log.i("Response message:", response.message)
                    onUpdateViewListener.updateView(response.body?.string())
                }
            }
        })
    }

}
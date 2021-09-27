package com.sony.sony

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * using for parsing the external localization json
 */
object Utils {
    fun getParseLocalization(responseString: String, holder: LocDataModel, pKey: String) {
        try {
            val jsonObject = JSONObject(responseString)
            if (jsonObject != null) {
                val jsonObject1 = jsonObject.getJSONObject("loc")
                if (jsonObject1 != null) {
                    holder.dictionaries.dicEng = getMap(holder.dictionaries.dicEng, jsonObject1.getJSONArray("en"), pKey)
                    holder.dictionaries.dicHindi = getMap(holder.dictionaries.dicHindi, jsonObject1.getJSONArray("hindi"), pKey)
                    holder.dictionaries.dicChinese = getMap(holder.dictionaries.dicChinese, jsonObject1.getJSONArray("chinese"), pKey)
                }
            }
            Log.d("malviya", " holder $holder")
        } catch (e: Exception) {
            Log.e("Utils", "Exception " + e.message)
        }
    }

    @Throws(JSONException::class)
    private fun getMap(map: HashMap<String, String>, jsonArray: JSONArray?, pKey: String): HashMap<String, String> {
        if (jsonArray != null) {
            for (index in 0 until jsonArray.length()) {
                val value = jsonArray.getJSONObject(index).getString(pKey)
                map[pKey] = value
            }
        }
        return map
    }
}
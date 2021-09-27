package com.sony.sony

/*
{
"loc": {
"en": [
 {
   "msg_welcome": "Hello World"
 }
],
"chinese": [
 {
   "msg_welcome": "你好，世界"
 }
],
"hindi": [
 {
   "msg_welcome": "नमस्ते दुनिया"
 }
]
}
}*/

/**
 * this model is used for holding the external localization
 */
class LocDataModel private constructor() {

    val dictionaries: Data

    companion object {
        private var mInstance: LocDataModel? = null
        val instance: LocDataModel
            get() {
                if (mInstance == null) {
                    mInstance = LocDataModel()
                }
                return mInstance as LocDataModel
            }
    }

    init {
        dictionaries = Data()
    }

    inner class Data {
        var dicEng: HashMap<String, String> = HashMap()
        var dicHindi: HashMap<String, String> = HashMap()
        var dicChinese: HashMap<String, String> = HashMap()
    }

    /**
     * used to pass in xml to complete the data binding process
     */
    inner class CustMap(map: HashMap<String, String>?) {
        var map: HashMap<String, String>?
        init {
            this.map = map
        }
    }

}
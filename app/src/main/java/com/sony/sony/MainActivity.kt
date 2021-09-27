package com.sony.sony

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sony.sony.databinding.ActivityMainBinding

/**
 * This class shows the Home page which has message in center of the screen.
 * Message's localization can be changed by clicking on the lang button.
 * Here, we do the following
 * 1.fetch the external localization
 * 2.applied data binding
 */
class MainActivity : AppCompatActivity(), View.OnClickListener, APIManager.OnUpdateListener {
    private var mMainViewModel: MainViewModel? = null
    private var mBinding: ActivityMainBinding? = null
    private var mHandler: Handler? = null
    private var mRunnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init() {
        initHandler()
        fetchLocalization()
        initListners()
        initViewModel()
        setDafaultLocalization()
    }

    private fun setDafaultLocalization() {
        ClientApplication.mapAppLocalization?.let { mMainViewModel?.updateMessage(it) }
    }

    private fun initListners() {
        mBinding?.buttonHindi?.setOnClickListener(this)
        mBinding?.buttonChinese?.setOnClickListener(this)
        mBinding?.buttonEng?.setOnClickListener(this)
    }

    private fun initHandler() {
        mHandler = Looper.myLooper()?.let { Handler(it) }
        mRunnable = Runnable { mBinding?.progressBar?.visibility = View.GONE }
    }

    private fun fetchLocalization() {
        mBinding?.progressBar?.visibility = View.VISIBLE
        APIManager.instance.getLocalization(APIManager.API.LOCALIZATION, this);
    }

    private fun initViewModel() {
        mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mMainViewModel?.updateMessage()?.observe(this, object : Observer<HashMap<String, String>> {
            override fun onChanged(t: HashMap<String, String>?) {
                mBinding?.obj = LocDataModel.instance.CustMap(t)
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_hindi -> if (LocDataModel.instance.dictionaries.dicHindi.size != 0) {
                mMainViewModel?.updateMessage(LocDataModel.instance.dictionaries.dicHindi)
            }
            R.id.button_eng -> if (LocDataModel.instance.dictionaries.dicEng.size != 0) {
                mMainViewModel?.updateMessage(LocDataModel.instance.dictionaries.dicEng)
            }
            R.id.button_chinese -> if (LocDataModel.instance.dictionaries.dicChinese.size != 0) {
                mMainViewModel?.updateMessage(LocDataModel.instance.dictionaries.dicChinese)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRunnable?.let { mHandler?.removeCallbacks(it) }
        mHandler = null
    }

    override fun updateView(responseString: String?) {
        mRunnable?.let { mHandler?.post(it) }
        if (!TextUtils.isEmpty(responseString.toString())) {
            for (key in ClientApplication.mapAppLocalization?.keys!!) {
                responseString?.let { Utils.getParseLocalization(it, LocDataModel.instance, key) }
            }
        }
    }
}
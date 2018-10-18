package com.dinesh.kotlinstructure.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.dinesh.kotlinstructure.R

open class LauncherActivity : AppCompatActivity() {

    val DELAY_TIME: Long = 1500

    var mHandler: Handler = Handler()

    private var waitRunnable: Runnable = Runnable {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        waitHandler()
    }

    fun waitHandler(mWait: Long = DELAY_TIME) {
        mHandler.postDelayed(waitRunnable, mWait)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mHandler.removeCallbacks(waitRunnable)
    }


}

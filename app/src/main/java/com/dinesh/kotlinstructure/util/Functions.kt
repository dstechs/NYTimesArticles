package com.dinesh.kotlinstructure.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.annotation.ColorInt
import android.support.annotation.NonNull
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.dinesh.kotlinstructure.R


object Functions {
    fun showToast(mMessage: String, mContext: Context?) {
        mContext?.run { Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT).show() }
    }

    fun showProgress(mContext: Context) {
        if (dialog != null && dialog!!.isShowing)
            return
        dialog = Dialog(mContext)
        dialog!!.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.setContentView(LayoutInflater.from(mContext).inflate(R.layout.item_progress, null))
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
    }

    fun hideProgress() {
        dialog?.dismiss()
    }

    var dialog: Dialog? = null

    fun hideKeypad(mActivity: Activity) {
        try {
            mActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        } catch (exceptin: NullPointerException) {
        }
    }

    fun hideKeypad(view: View, context: Context) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun overflowIconColor(@NonNull toolbar: Toolbar, @ColorInt color: Int?): Boolean {
        val overflowIcon = toolbar.getOverflowIcon() ?: return false
        val colorFilter = if (color == null) null else PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY)
        overflowIcon.colorFilter = colorFilter
        return true
    }
}
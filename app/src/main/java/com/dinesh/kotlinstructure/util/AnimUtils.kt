package com.dinesh.kotlinstructure.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.View


fun inAnimationScaleFade(view: View, delay: Long) {
//    view.alpha = 0f
    val scaleX = ObjectAnimator.ofFloat(view, "translationY", 100f, 0f)
    scaleX.duration = 500
    scaleX.start()
}

class AnimUtils {

    companion object {
        fun startCircularActivity(mActivity: AppCompatActivity, view: View, intent: Intent) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view, "transition")
            val revealX = view.width
            val revealY = view.height
            intent.putExtra("REVEAL_X", revealX)
            intent.putExtra("REVEAL_Y", revealY)
            ActivityCompat.startActivity(mActivity, intent, options.toBundle())
        }

        fun translateAnimation(view: View?, delay: Long) {
            view?.run {
                translate(view, delay)
            }
        }

        fun translate(view: View, delay: Long = 0, duration: Long = 200) {
            view.visibility = View.VISIBLE
            view.alpha = 0.0f
            val translate = ObjectAnimator.ofFloat(view, "translationY", 100f, 0f)
            val alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
            val animator = AnimatorSet()
            animator.playTogether(translate, alpha)
            animator.startDelay = delay
            animator.duration = duration
            animator.start()
        }
    }
}
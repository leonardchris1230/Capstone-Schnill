package com.lazibear.capstone_schnill.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener

fun View.startAnimation(animation: Animation, onEnd: () -> Unit) {

    animation.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(p0: Animation?) = Unit

        override fun onAnimationEnd(p0: Animation?) {
            onEnd()
        }

        override fun onAnimationRepeat(p0: Animation?) = Unit


    })
    this.startAnimation(animation)

}


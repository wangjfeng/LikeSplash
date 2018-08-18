/*
 *   Copyright 2018 Google LLC
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.github.jasonhezz.likesplash.util.recyclerview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import java.util.ArrayList

/**
 * A [RecyclerView.ItemAnimator] that fades & slides newly added items in from a given
 * direction.
 */
class SlideInItemAnimator @JvmOverloads constructor(slideFromEdge: Int = Gravity.BOTTOM, layoutDirection: Int = -1) :
    DefaultItemAnimator() {

    private val pendingAdds = ArrayList<RecyclerView.ViewHolder>()
    private val slideFromEdge: Int = Gravity.getAbsoluteGravity(slideFromEdge, layoutDirection)

    init {
        addDuration = 160L
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.alpha = 0f
        when (slideFromEdge) {
            Gravity.LEFT -> holder.itemView.translationX = (-holder.itemView.width / 3).toFloat()
            Gravity.TOP -> holder.itemView.translationY = (-holder.itemView.height / 3).toFloat()
            Gravity.RIGHT -> holder.itemView.translationX = (holder.itemView.width / 3).toFloat()
            else // Gravity.BOTTOM
            -> holder.itemView.translationY = (holder.itemView.height / 3).toFloat()
        }
        pendingAdds.add(holder)
        return true
    }

    override fun runPendingAnimations() {
        super.runPendingAnimations()
        if (!pendingAdds.isEmpty()) {
            for (i in pendingAdds.indices.reversed()) {
                val holder = pendingAdds[i]
                holder.itemView.animate()
                    .alpha(1f)
                    .translationX(0f)
                    .translationY(0f)
                    .setDuration(addDuration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            dispatchAddStarting(holder)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            animation.listeners.remove(this)
                            dispatchAddFinished(holder)
                            dispatchFinishedWhenDone()
                        }

                        override fun onAnimationCancel(animation: Animator) {
                            clearAnimatedValues(holder.itemView)
                        }
                    }).interpolator =
                    AnimationUtils.loadInterpolator(holder.itemView.context, android.R.interpolator.linear_out_slow_in)
                pendingAdds.removeAt(i)
            }
        }
    }

    override fun endAnimation(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().cancel()
        if (pendingAdds.remove(holder)) {
            dispatchAddFinished(holder)
            clearAnimatedValues(holder.itemView)
        }
        super.endAnimation(holder)
    }

    override fun endAnimations() {
        for (i in pendingAdds.indices.reversed()) {
            val holder = pendingAdds[i]
            clearAnimatedValues(holder.itemView)
            dispatchAddFinished(holder)
            pendingAdds.removeAt(i)
        }
        super.endAnimations()
    }

    override fun isRunning(): Boolean {
        return !pendingAdds.isEmpty() || super.isRunning()
    }

    private fun dispatchFinishedWhenDone() {
        if (!isRunning) {
            dispatchAnimationsFinished()
        }
    }

    private fun clearAnimatedValues(view: View) {
        view.alpha = 1f
        view.translationX = 0f
        view.translationY = 0f
    }
}

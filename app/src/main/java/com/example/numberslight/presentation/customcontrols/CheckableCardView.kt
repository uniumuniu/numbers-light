package com.example.numberslight.presentation.customcontrols

import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable

import androidx.cardview.widget.CardView


class CheckableCardView(context: Context, attrs: AttributeSet) : CardView(context, attrs), Checkable {
    private var isChecked: Boolean = false

    private val checkedStateSet = intArrayOf(
        android.R.attr.state_checked
    )

    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }

    override fun setChecked(checked: Boolean) {
        this.isChecked = checked
    }

    override fun isChecked(): Boolean {
        return isChecked
    }

    override fun toggle() {
        isChecked = !this.isChecked
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray? {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(drawableState, checkedStateSet)
        }

        return drawableState
    }
}

package com.example.paperscissorsstone

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable

class CustomerRipple {
    private var mSelectedColor: Int = Color.parseColor("#C4C4C4")
    private var mBackgroundColor: Int = 0
    private var mDrawable: Drawable? = null
    private var mStrokeColor: Int = 0
    private var mStrokeWidth: Int = 0
    private var mRadius: Int = 0



    constructor() {
        mBackgroundColor = Color.parseColor("#ffffff")
        mRadius = 0
        mStrokeColor = -1
        mBackgroundColor = -1
    }

    /*
    * mBackgroundColor 背景色
    * mRadius 圓角
    **/
    constructor(mBackgroundColor: Int, mRadius: Int) {
        this.mBackgroundColor = mBackgroundColor
        this.mRadius = mRadius
        mStrokeColor = -1
        mStrokeWidth = -1
    }

    /*
    * mBackgroundColor 背景色
    * mStrokeColor 邊框顏色
    * mStrokeWidth 邊框粗度
    * mRadius 圓角
    **/
    constructor(mBackgroundColor: Int, mStrokeColor: Int, mStrokeWidth: Int, mRadius: Int) {
        this.mBackgroundColor = mBackgroundColor
        this.mStrokeColor = mStrokeColor
        this.mStrokeWidth = mStrokeWidth
        this.mRadius = mRadius
    }

    /*
    * mDrawable 圖片
    **/
    constructor(mDrawable: Drawable?) {
        this.mDrawable = mDrawable
    }

    fun setSelectedColor(mSelectedColor: Int) {
        this.mSelectedColor = mSelectedColor
    }

    fun rippleDrawable(): RippleDrawable {
        val stateList = arrayOf(
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_activated),
            intArrayOf()
        )

        val stateColorList =
            intArrayOf(mSelectedColor, mSelectedColor, mSelectedColor, mSelectedColor)

        val colorStateList = ColorStateList(stateList, stateColorList)

        return if (mDrawable == null) {
            val contentDrawable = GradientDrawable()
            if (mStrokeColor != -1 && mStrokeWidth != -1) {//有邊框時
                contentDrawable.setColor(mBackgroundColor)
                contentDrawable.cornerRadius = mRadius.toFloat()
                contentDrawable.setStroke(mStrokeWidth, mStrokeColor)
                RippleDrawable(colorStateList, contentDrawable, contentDrawable)
            } else {//純背景色和圓角
                contentDrawable.setColor(mBackgroundColor)
                contentDrawable.cornerRadius = mRadius.toFloat()
                RippleDrawable(colorStateList, contentDrawable, contentDrawable)
            }
        } else {//底圖為圖片
            RippleDrawable(colorStateList, mDrawable, mDrawable)
        }
    }

}
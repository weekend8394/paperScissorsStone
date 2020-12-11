package com.example.paperscissorsstone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.yesButton
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    /**
     * 0剪刀
     * 1石頭
     * 2布
     * */
    private var mUser = -1 //使用者的出拳代號
    private var mUserCounter = 0 //使用者贏的場數
    private var mComputerCounter = 0 //電腦贏的場數
    private val mImageViewAlpha = 0.3F //圖片alpha值

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initClick()
    }

    private fun initView() {
        val customRipple = CustomerRipple(ContextCompat.getColor(this, R.color.colorBlue), 50)
        customRipple.setSelectedColor(ContextCompat.getColor(this, R.color.colorWhite))
        tv_start.background = customRipple.rippleDrawable()
    }

    private fun initClick() {
        tv_start.onClick {
            if (mUser >= 0) {
                animation()
                isImageViewEnable(false)
            } else {
                Toast.makeText(this@MainActivity, "請選擇你的出拳~", Toast.LENGTH_SHORT).show()
            }
        }

        bt_restart.onClick {
            mUserCounter = 0
            mComputerCounter = 0
            mUser = -1
            tv_user_counter.text = mUserCounter.toString()
            tv_computerCounter.text = mComputerCounter.toString()
            tv_start.visibility = View.VISIBLE
            iv_ComputerResult.background = null
            iv_paper.alpha = 1F
            iv_scissors.alpha = 1F
            iv_stone.alpha = 1F
        }

        iv_scissors.onClick {
            mUser = 0
            setImageViewAlpha(iv_scissors, iv_paper, iv_stone)
        }

        iv_stone.onClick {
            mUser = 1
            setImageViewAlpha(iv_stone, iv_paper, iv_scissors)
        }

        iv_paper.onClick {
            mUser = 2
            setImageViewAlpha(iv_paper, iv_scissors, iv_stone)
        }
    }

    /**
     * millisInFuture : 總時長 2秒
     * countDownInterval : 每0.05秒進入onTick方法
     * */
    private fun animation() {
        object : CountDownTimer(2000, 50) {
            override fun onFinish() {
                main()
            }

            override fun onTick(p0: Long) {
                setImageView(iv_ComputerResult, Random.nextInt(3))
            }
        }.start()
    }

    private fun setImageView(view: ImageView, num: Int) {
        when (num) {
            0 -> view.background = ContextCompat.getDrawable(this, R.drawable.ic_scissors)
            1 -> view.background = ContextCompat.getDrawable(this, R.drawable.ic_stone)
            2 -> view.background = ContextCompat.getDrawable(this, R.drawable.ic_paper)
        }
    }

    private fun isImageViewEnable(isEnable: Boolean) {
        tv_start.isEnabled = isEnable
        iv_scissors.isEnabled = isEnable
        iv_stone.isEnabled = isEnable
        iv_paper.isEnabled = isEnable
    }

    private fun setImageViewAlpha(selectedView: View, otherView1: ImageView, otherView2: ImageView) {
        selectedView.alpha = 1F
        otherView1.alpha = mImageViewAlpha
        otherView2.alpha = mImageViewAlpha
    }

    private fun main() {
        val computer = Random.nextInt(3)
        Log.d("123 : ", computer.toString())
        setImageView(iv_ComputerResult, computer)
        isImageViewEnable(true)

        //電腦與user的出拳(0剪刀,1石頭,2布)相減
        //贏的情況 剪刀,布   -2   石頭,剪刀 1   布,石頭 1
        //輸的情況 剪刀,石頭 -1   石頭,布 -1    布,剪刀 2
        //平手 0
        when (mUser - computer) {
            -2, 1 -> {
                mUserCounter++
                Toast.makeText(this, "贏啦", Toast.LENGTH_SHORT).show()
            }
            -1, 2 -> {
                mComputerCounter++
                Toast.makeText(this, "QQ", Toast.LENGTH_SHORT).show()
            }
            0 -> {
                Toast.makeText(this, "平手", Toast.LENGTH_SHORT).show()
            }
        }

        tv_user_counter.text = mUserCounter.toString()
        tv_computerCounter.text = mComputerCounter.toString()

        if ((mUserCounter + mComputerCounter) == 5) {
            tv_start.visibility = View.GONE
            bt_restart.visibility = View.VISIBLE
            if (mUserCounter > mComputerCounter) {
                alert("You Win") {
                    yesButton { }
                }.show()
            } else {
                alert("You Lose") {
                    yesButton { }
                }.show()
            }
        }
    }
}
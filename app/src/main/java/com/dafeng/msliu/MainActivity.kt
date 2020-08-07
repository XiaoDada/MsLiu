package com.dafeng.msliu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dafeng.msliu.function.activity.ViewpagePagingActivity
import com.dafeng.msliu.touchtest.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onButtonClick(v: View) {
        when (v.id) {
            R.id.btn_first -> jump(FirstActivity::class.java)
            R.id.btn_second -> jump(SecondActivity::class.java)
            R.id.btn_third -> jump(ThreeActivity::class.java)
            R.id.btn_four -> jump(FourActivity::class.java)
            R.id.btn_five -> jump(FiveActivity::class.java)
            R.id.btn_six -> jump(MorePointViewActivity::class.java)
            R.id.btn_seven -> jump(ViewpagePagingActivity::class.java)
            else -> {
            }
        }
    }

    fun jump(clz: Class<out Activity?>?) {
        val intent = Intent(this, clz)
        startActivity(intent)
    }
}
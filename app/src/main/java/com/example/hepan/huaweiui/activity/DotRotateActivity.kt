package com.example.hepan.huaweiui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.hepan.huaweiui.view.DotRotateAndCheckMark

class DotRotateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = DotRotateAndCheckMark(this)
        setContentView(view)
    }
}

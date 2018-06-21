package com.example.hepan.huaweiui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.hepan.huaweiui.R
import kotlinx.android.synthetic.main.activity_dot_rotate.*

class DotRotateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dot_rotate)

        btnDrawMark.setOnClickListener {
            dotRotate.drawCheckMark()
        }
    }
}

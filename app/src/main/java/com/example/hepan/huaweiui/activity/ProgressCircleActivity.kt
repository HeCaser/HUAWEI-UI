package com.example.hepan.huaweiui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.example.hepan.huaweiui.R
import kotlinx.android.synthetic.main.activity_progress_circle.*

class ProgressCircleActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        progressCircle.setmProgress(progress)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_progress_circle)
        btnStart.setOnClickListener {
            progressCircle.startCircleRun()
        }
        seekBar.setOnSeekBarChangeListener(this)
    }
}

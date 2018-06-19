package com.example.hepan.huaweiui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.hepan.huaweiui.R
import com.example.hepan.huaweiui.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvShow.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            this.adapter = MainAdapter(getList()) {
                goActivity(it)
            }
        }
    }

    private fun goActivity(pos: Int) {
        when (pos) {
            0 -> {
                var sc = Intent(this, ProgressCircleActivity::class.java)
                startActivity(sc)
            }
        }
    }

    fun getList(): List<String> {

        var l = mutableListOf("加载\n小圆球")
        return l
    }
}

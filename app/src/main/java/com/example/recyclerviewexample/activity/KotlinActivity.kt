package com.example.recyclerviewexample.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerviewexample.R
import com.example.recyclerviewexample.kotlin.KotlinDemo

class KotlinActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_kotlin
    }

    override fun initView() {
    }

    override fun init() {
        var kotlinDemo = KotlinDemo()
    }
}

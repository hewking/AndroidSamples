package com.hewking.develop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hewking.develop.demo.DemoListFragment
import com.hewking.develop.service.CountDownService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout,DemoListFragment(),"DemoListFragment")
            .commit()
//        btn_start.setOnClickListener {
//            startService(Intent(this@MainActivity,CountDownService::class.java))
//        }

    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}

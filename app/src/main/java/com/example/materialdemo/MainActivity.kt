package com.example.materialdemo

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.materialdemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    private var x1 = 0f
    private var x2 = 0f
    private var y1 = 0f
    private var y2 = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //设置标题栏
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.let {
            //显示侧滑图标
            it.setDisplayHomeAsUpEnabled(true)
            //指定侧滑图标的样式
            it.setHomeAsUpIndicator(R.drawable.ic_nav)
        }
        //默认选中
        mBinding.navView.setCheckedItem(R.id.navCall)
        //点击了选项的事件
        mBinding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navCall -> Toast.makeText(this, "电话", Toast.LENGTH_SHORT).show()
                R.id.navBlock -> Toast.makeText(this, "锁", Toast.LENGTH_SHORT).show()
                R.id.navDown -> Toast.makeText(this, "下载", Toast.LENGTH_SHORT).show()
                R.id.navLoad -> Toast.makeText(this, "定位", Toast.LENGTH_SHORT).show()
                R.id.navMsg -> Toast.makeText(this, "消息", Toast.LENGTH_SHORT).show()
            }
            //关闭侧滑
            mBinding.drawerLayout.closeDrawers()
            true
        }

        mBinding.constraintLayout.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    //当手指按下的时候
                    x1 = motionEvent.x
                    y1 = motionEvent.y
                }
                MotionEvent.ACTION_UP -> {
                    //当手指离开的时候
                    x2 = motionEvent.x
                    y2 = motionEvent.y
                    when {
                        y1 - y2 > 50 -> Log.i("滑动方向", "向上滑")
                        y2 - y1 > 50 -> Log.i("滑动方向", "向下滑")
                        x1 - x2 > 50 -> {
                            Log.i("滑动方向", "向左滑")
                            //mBinding.drawerLayout.openDrawer(GravityCompat.END)
                        }
                        x2 - x1 > 50 -> {
                            Log.i("滑动方向", "向右滑")
                            mBinding.drawerLayout.openDrawer(GravityCompat.START)
                        }
                    }
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.no_see -> Toast.makeText(this, "你点击了不可见图标", Toast.LENGTH_SHORT).show()
            R.id.msg -> Toast.makeText(this, "你点击了消息图标", Toast.LENGTH_SHORT).show()
            R.id.count -> Toast.makeText(this, "你点击了统计图标", Toast.LENGTH_SHORT).show()
            android.R.id.home -> mBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }
}
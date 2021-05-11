package com.example.materialdemo

import android.annotation.SuppressLint
import android.graphics.Color
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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.materialdemo.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    private var x1 = 0f
    private var x2 = 0f
    private var y1 = 0f
    private var y2 = 0f

    private val fruit = mutableListOf(
        Fruit("草莓", R.drawable.fruit_1),
        Fruit("苹果", R.drawable.fruit_2),
        Fruit("樱桃", R.drawable.fruit_3),
        Fruit("猕猴桃", R.drawable.fruit_4),
        Fruit("橙子", R.drawable.fruit_5),
        Fruit("柠檬", R.drawable.fruit_6),
        Fruit("山竹", R.drawable.fruit_7)
    )

    private val fruitList = ArrayList<Fruit>()

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

        mBinding.recyclerView.setOnTouchListener { _, motionEvent ->
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
            false
        }

        mBinding.fab.setOnClickListener { view ->
            Snackbar.make(view, "你点击了悬浮按钮", Snackbar.LENGTH_LONG)
                .setAction("我可以被点击") {
                    Snackbar.make(it, "你点击了弹出式通知", Snackbar.LENGTH_SHORT).show()
                }
                .show()
        }

        initFruit()
        //网格布局
        mBinding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        val adapter = FruitAdapter(this, fruitList)
        mBinding.recyclerView.adapter = adapter

        mBinding.switchMaterial.setColorSchemeResources(R.color.red)
        mBinding.switchMaterial.setOnRefreshListener {
            refresh(adapter)
        }
    }

    private fun refresh(adapter: FruitAdapter) {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruit()
                adapter.notifyDataSetChanged()
                mBinding.switchMaterial.isRefreshing = false
            }
        }
    }

    private fun initFruit() {
        fruitList.clear()
        repeat(50)
        {
            val index = (0 until fruit.size).random()
            fruitList.add(fruit[index])
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
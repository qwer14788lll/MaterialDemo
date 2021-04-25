package com.example.materialdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.materialdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //设置标题栏
        setSupportActionBar(mBinding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.no_see->Toast.makeText(this,"你点击了不可见图标",Toast.LENGTH_SHORT).show()
            R.id.msg->Toast.makeText(this,"你点击了消息图标",Toast.LENGTH_SHORT).show()
            R.id.count->Toast.makeText(this,"你点击了统计图标",Toast.LENGTH_SHORT).show()
        }
        return true
    }
}
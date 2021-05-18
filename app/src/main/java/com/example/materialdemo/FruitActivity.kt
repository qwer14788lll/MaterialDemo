package com.example.materialdemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.example.materialdemo.databinding.ActivityFruitBinding
import com.example.materialdemo.databinding.FruitItemBinding

class FruitActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityFruitBinding

    companion object {
        const val FRUIT_NAME = "fruit_name"
        const val FRUIT_IMG_ID = "fruit_img_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFruitBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val fruitName = intent.getStringExtra(FRUIT_NAME) ?: "Test"
        val fruitImgID = intent.getIntExtra(FRUIT_IMG_ID, 0)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.collapsingToolbarLayout.title = fruitName
        Glide.with(this).load(fruitImgID).into(mBinding.fruitImageView)
        mBinding.fruitText.text = initText(fruitName)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 水果名重复5000遍
     */
    private fun initText(fruitName: String) = fruitName.repeat(5000)
}
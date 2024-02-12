package com.example.fintechfilms.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fintechfilms.R
import com.example.fintechfilms.db.FavouriteRepository

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListFragment())
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

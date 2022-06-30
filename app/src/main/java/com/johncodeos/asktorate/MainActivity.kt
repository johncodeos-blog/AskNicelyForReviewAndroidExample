package com.johncodeos.asktorate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: SocialRVAdapter
    private var demoData = SocialDataArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val socialRv = findViewById<RecyclerView>(R.id.social_rv)

        adapter = SocialRVAdapter(demoData)
        adapter.notifyDataSetChanged()
        socialRv.adapter = adapter

        val mLayoutManager = LinearLayoutManager(socialRv.context)
        socialRv.layoutManager = mLayoutManager
        socialRv.setHasFixedSize(true)

    }

}

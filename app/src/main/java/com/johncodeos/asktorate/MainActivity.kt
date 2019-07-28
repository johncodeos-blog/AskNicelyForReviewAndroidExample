package com.johncodeos.asktorate

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.stkent.amplify.prompt.BasePromptViewConfig
import com.github.stkent.amplify.prompt.DefaultLayoutPromptView
import com.github.stkent.amplify.prompt.DefaultLayoutPromptViewConfig
import com.github.stkent.amplify.tracking.Amplify
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter : Social_RVAdapter
    var demoData = SocialDataArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = Social_RVAdapter(demoData)
        adapter.notifyDataSetChanged()
        social_rv.adapter = adapter

        val mLayoutManager = LinearLayoutManager(social_rv.context)
        social_rv.layoutManager = mLayoutManager
        social_rv.setHasFixedSize(true)

    }

}

package com.example.whysky


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.whysky.models.Whisky
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import com.example.whysky.service.WhiskyService
class WhiskyList: AppCompatActivity(){
    private lateinit var whiskys:ArrayList<Whisky>

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: WhiskyAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.whisky_list)

        requestQueue = Volley.newRequestQueue(this)

        whiskys = ArrayList<Whisky>()

        viewManager = LinearLayoutManager(this)

        viewAdapter = WhiskyAdapter(whiskys, this)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewWhisky)
        // use a linear layout manager
        recyclerView.layoutManager = viewManager

        // specify an viewAdapter (see also next example)
        recyclerView.adapter = viewAdapter
        getAllWhisky()
        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intent = Intent(this, WhiskyDetail::class.java)
            intent.putExtra("state", "Adding")
            startActivity(intent)
        }
    }
            private fun getAllWhisky() {
                val whiskyService = WhiskyService()
                whiskyService.getAll(this) { response ->
                    run {
                        if (response != null) {
                            viewAdapter.whiskyList = response
                        }
                        viewAdapter.notifyDataSetChanged()
                    }
                }


            }}






package com.example.bicycles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.bicycles.models.Bicycle
import com.example.bicycles.service.BicycleServiceImpl
import com.example.bicycles.service.BicycleSingleton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException

class BicycleListActivity : AppCompatActivity() {
    private lateinit var bicycles: ArrayList<Bicycle>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: BicycleAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bicycle_list)

        bicycles = ArrayList<Bicycle>()

        viewManager = LinearLayoutManager(this)

        viewAdapter = BicycleAdapter(bicycles, this)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBicycles)
        // use a linear layout manager
        recyclerView.layoutManager = viewManager

        // specify an viewAdapter (see also next example)
        recyclerView.adapter = viewAdapter

        getAllBicycles()

        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener{
            val intent = Intent(this, BicycleDetailActivity::class.java)
            intent.putExtra("state", "Adding")
            startActivity(intent)
        }
    }

    private fun getAllBicycles() {

        val bicycleServiceImpl = BicycleServiceImpl()
        bicycleServiceImpl.getAll(this) { response ->
            run {
                if (response != null) {
                    viewAdapter.bicycleList = response
                }
                viewAdapter.notifyDataSetChanged()
            }
        }
    }

}


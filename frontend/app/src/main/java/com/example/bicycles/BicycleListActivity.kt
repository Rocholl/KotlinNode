package com.example.bicycles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bicycles.models.Bicycle

class BicycleListActivity : AppCompatActivity() {
    private lateinit var bicycles: ArrayList<Bicycle>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: BicycleAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bicycle_list)

        viewManager = LinearLayoutManager(this)
        viewAdapter = BicycleAdapter(bicycles)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBicycles).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        getAllBicycles()

        view
    }

    private fun getAllBicycles() {
        bicycles = ArrayList<Bicycle>()

        bicycles.add(Bicycle("BH", "star"))
        bicycles.add(Bicycle("Orbea", "machine"))
    }


}
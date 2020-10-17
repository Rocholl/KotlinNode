package com.example.bicycles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.bicycles.models.Bicycle
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

        viewAdapter = BicycleAdapter(bicycles)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBicycles)
        // use a linear layout manager
        recyclerView.layoutManager = viewManager

        // specify an viewAdapter (see also next example)
        recyclerView.adapter = viewAdapter

        getAllBicycles()

        (recyclerView.adapter as BicycleAdapter).notifyDataSetChanged()
    }

    private fun getAllBicyclesLocally() {
        //bicycles = ArrayList<Bicycle>()

        bicycles.add(Bicycle("BH", "star"))
        bicycles.add(Bicycle("Orbea", "machine"))
    }

    private fun getAllBicycles() {
        val url = "http://192.168.0.164:8080/api/bicycles"
        val request =
                JsonArrayRequest(Request.Method.GET, url, null, { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val bicycle = response.getJSONObject(i)
                            val model = bicycle.getString("model")
                            val brand = bicycle.getString("brand")
                            bicycles.add(Bicycle(brand, model))
                        }
                        viewManager.bicycleList = bicycles
                        viewAdapter.notifyDataSetChanged()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }
}


package com.example.bicycles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.bicycles.models.Bicycle
import org.json.JSONException

class BicycleListActivity : AppCompatActivity() {
    private lateinit var bicycles: ArrayList<Bicycle>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: BicycleAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bicycle_list)

        requestQueue = Volley.newRequestQueue(this)

        bicycles = ArrayList<Bicycle>()

        viewManager = LinearLayoutManager(this)

        viewAdapter = BicycleAdapter(bicycles, this)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBicycles)
        // use a linear layout manager
        recyclerView.layoutManager = viewManager

        // specify an viewAdapter (see also next example)
        recyclerView.adapter = viewAdapter

        getAllBicycles()

    }

    private fun getAllBicycles() {

        val url = "http://192.168.0.164:8080/api/bicycles"
        val request =
                JsonArrayRequest(Request.Method.GET, url, null, { response ->
                    try {
                        Log.v("hola caracola", response.toString())
                        for (i in 0 until response.length()) {
                            val bicycle = response.getJSONObject(i)
                            val id = bicycle.getInt("id")
                            val model = bicycle.getString("model")
                            val brand = bicycle.getString("brand")
                            bicycles.add(Bicycle(id, brand, model))
                            Log.v("hola caracola", bicycles.get(i).id.toString())
                        }
                        viewAdapter.bicycleList = bicycles
                        viewAdapter.notifyDataSetChanged()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }
}


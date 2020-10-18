package com.example.bicycles.service

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.bicycles.models.Bicycle
import org.json.JSONObject

class BicycleServiceImpl : IBicycleService {
    override fun getAll(context: Context, completionHandler: (response: ArrayList<Bicycle>?) -> Unit) {
        val path = BicycleSingleton.getInstance(context).baseUrl + "/api/bicycles"
        val arrayRequest = JsonArrayRequest(Request.Method.GET, path, null,
                { response ->
                    var bicycles: ArrayList<Bicycle> = ArrayList()
                    for (i in 0 until response.length()) {
                        val bicycle = response.getJSONObject(i)
                        val id = bicycle.getInt("id")
                        val model = bicycle.getString("model")
                        val brand = bicycle.getString("brand")
                        bicycles.add(Bicycle(id, brand, model))
                    }
                    completionHandler(bicycles)
                },
                { error ->
                    completionHandler(ArrayList<Bicycle>())
                })
        BicycleSingleton.getInstance(context).addToRequestQueue(arrayRequest)
    }

    override fun getById(context: Context, bicycleId: Int, completionHandler: (response: Bicycle?) -> Unit) {
        val path = BicycleSingleton.getInstance(context).baseUrl + "/api/bicycles/" + bicycleId
        val objectRequest = JsonObjectRequest(Request.Method.GET, path, null,
                { response ->
                    if(response == null) completionHandler(null)

                    val id = response.getInt("id")
                    val model = response.getString("model")
                    val brand = response.getString("brand")

                    val bicycle = Bicycle(id,brand,model)
                    completionHandler(bicycle)
                },
                { error ->
                    completionHandler(null)
                })
        BicycleSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun deleteById(context: Context, bicycleId: Int, completionHandler: () -> Unit) {
        val path = BicycleSingleton.getInstance(context).baseUrl + "/api/bicycles/" + bicycleId
        val objectRequest = JsonObjectRequest(Request.Method.DELETE, path, null,
                { response ->
                    Log.v("Hola caracola", "se borró")
                    completionHandler()
                },
                { error ->
                    Log.v("Hola caracola", "dió error")
                    completionHandler()
                })
        BicycleSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun updateBicycle(context: Context, bicycle: Bicycle, completionHandler: () -> Unit) {
        val path = BicycleSingleton.getInstance(context).baseUrl + "/api/bicycles/" + bicycle.id
        val bicycleJson: JSONObject = JSONObject()
        bicycleJson.put("id", bicycle.id.toString())
        bicycleJson.put("brand", bicycle.brand)
        bicycleJson.put("model", bicycle.model)

        val objectRequest = JsonObjectRequest(Request.Method.PUT, path, bicycleJson,
                { response ->
                    completionHandler()
                },
                { error ->
                    completionHandler()
                })
        BicycleSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun createBicycle(context: Context, bicycle: Bicycle, completionHandler: () -> Unit) {
        val path = BicycleSingleton.getInstance(context).baseUrl + "/api/bicycles"
        val bicycleJson: JSONObject = JSONObject()
        bicycleJson.put("id", bicycle.id.toString())
        bicycleJson.put("brand", bicycle.brand)
        bicycleJson.put("model", bicycle.model)

        val objectRequest = JsonObjectRequest(Request.Method.POST, path, bicycleJson,
                { response -> completionHandler() },
                { error -> completionHandler() })
        BicycleSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }
}
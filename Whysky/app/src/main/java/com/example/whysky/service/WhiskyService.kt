package com.example.whysky.service

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.whysky.models.Whisky
import org.json.JSONObject

class WhiskyService : iWhiskyService {
    override fun getAll(context: Context, completionHandler: (response: ArrayList<Whisky>?) -> Unit) {
        val path = WhiskySingleton.getInstance(context).baseUrl + "/api/whisky/"
        val arrayRequest = JsonArrayRequest(
            Request.Method.GET, path, null,
            { response ->
                var whisky: ArrayList<Whisky> = ArrayList()
                for (i in 0 until response.length()) {
                    val whiskys = response.getJSONObject(i)
                    val id = whiskys.getInt("id")
                    val nombre = whiskys.getString("nombre")
                    val procedencia = whiskys.getString("procedencia`")
                    val descripcion = whiskys.getString("descripcion")
                    val image = whiskys.getString("image")
                    whisky.add(Whisky(id, nombre, procedencia,descripcion,image))
                }
                completionHandler(whisky)
            },
            { error ->
                completionHandler(ArrayList<Whisky>())
            })
        WhiskySingleton.getInstance(context).addToRequestQueue(arrayRequest)
    }

    override fun getById(context: Context, whiskyId: Int, completionHandler: (response: Whisky?) -> Unit) {
        val path = WhiskySingleton.getInstance(context).baseUrl + "/api/whisky/" + whiskyId
        val objectRequest = JsonObjectRequest(
            Request.Method.GET, path, null,
            { response ->
                if(response == null) completionHandler(null)

                val id = response.getInt("id")
                val nombre = response.getString("nombre")
                val procedencia = response.getString("procedencia")
                val descripcion = response.getString("descripcion")
                val image = response.getString("image")

                val whiskys= Whisky(id, nombre, procedencia,descripcion,image)
                completionHandler(whiskys)
            },
            { error ->
                completionHandler(null)
            })
        WhiskySingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun deleteById(context: Context, whiskyId: Int, completionHandler: () -> Unit) {
        val path = WhiskySingleton.getInstance(context).baseUrl + "/api/whisky/" + whiskyId
        val objectRequest = JsonObjectRequest(
            Request.Method.DELETE, path, null,
            { response ->
                Log.v("Hola caracola", "se borró")
                completionHandler()
            },
            { error ->
                Log.v("Hola caracola", "dió error")
                completionHandler()
            })
        WhiskySingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun updateWhisky(context: Context, whisky: Whisky, completionHandler: () -> Unit) {
        val path = WhiskySingleton.getInstance(context).baseUrl + "/api/whisky/" + whisky.id
        val whiskyJson: JSONObject = JSONObject()
        whiskyJson.put("id", whisky.id.toString())
        whiskyJson.put("nombre", whisky.nombre)
        whiskyJson.put("descripcion", whisky.descripcion)
        whiskyJson.put("procedencia", whisky.procedencia)
        whiskyJson.put("image", whisky.image)

        val objectRequest = JsonObjectRequest(
            Request.Method.PUT, path, whiskyJson,
            { response ->
                completionHandler()
            },
            { error ->
                completionHandler()
            })
        WhiskySingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun createWhisky(context: Context,whisky: Whisky, completionHandler: () -> Unit) {
        val path = WhiskySingleton.getInstance(context).baseUrl + "/api/whisky"
        val whiskyJson: JSONObject = JSONObject()
        whiskyJson.put("id", whisky.id.toString())
        whiskyJson.put("nombre", whisky.nombre)
        whiskyJson.put("descripcion", whisky.descripcion)
        whiskyJson.put("procedencia", whisky.procedencia)
        whiskyJson.put("image", whisky.image)

        val objectRequest = JsonObjectRequest(
            Request.Method.POST, path, whiskyJson,
            { response -> completionHandler() },
            { error -> completionHandler() })
        WhiskySingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

}
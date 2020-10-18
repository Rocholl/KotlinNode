package com.example.bicycles.service

import android.content.Context
import com.example.bicycles.models.Bicycle
import org.json.JSONArray
import org.json.JSONObject

interface IBicycleService {
    fun getAll(context: Context, completionHandler: (response: ArrayList<Bicycle>?) -> Unit)

    fun getById(context: Context, bicycleId: Int, completionHandler: (response: Bicycle?) -> Unit)

    fun deleteById(context: Context, bicycleId: Int, completionHandler: () -> Unit)

    fun updateBicycle(context: Context, bicycle: Bicycle, completionHandler: () -> Unit)

    fun createBicycle(context: Context, bicycle: Bicycle, completionHandler: () -> Unit)
}
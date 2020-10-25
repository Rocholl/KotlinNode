package com.example.whysky.service

import android.content.Context
import com.example.whysky.models.Whisky

interface iWhiskyService {
    fun getAll(context: Context, completionHandler: (response: ArrayList<Whisky>?) -> Unit)

    fun getById(context: Context, whiskyId: Int, completionHandler: (response: Whisky?) -> Unit)

    fun deleteById(context: Context, whiskyId: Int, completionHandler: () -> Unit)

    fun updateWhisky(context: Context, whisky: Whisky, completionHandler: () -> Unit)

    fun createWhisky(context: Context, whisky: Whisky, completionHandler: () -> Unit)
}
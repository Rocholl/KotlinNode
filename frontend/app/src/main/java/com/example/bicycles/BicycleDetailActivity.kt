package com.example.bicycles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.bicycles.models.Bicycle
import com.example.bicycles.service.BicycleServiceImpl
import com.example.bicycles.service.BicycleSingleton
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

class BicycleDetailActivity : AppCompatActivity() {
    private lateinit var state: String
    private lateinit var textInputEditTextBrand: EditText
    private lateinit var textInputEditTextModel: EditText
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bicycle_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        state = this.intent.getStringExtra("state").toString()

        val bicycleId = this.intent.getIntExtra("bicycleId", 1)

        textInputEditTextBrand = findViewById(R.id.TextInputEditTextBrand)
        textInputEditTextModel = findViewById(R.id.TextInputEditTextModel)

        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            deleteBicycle(bicycleId)
        }

        if(state == "Showing") getBicycle(bicycleId)

        buttonEdit = findViewById(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            when(state){
                "Showing" -> {
                    changeButtonsToEditing()
                }
                "Editing" -> {
                    val bicycle = Bicycle(bicycleId, textInputEditTextBrand.text.toString(), textInputEditTextModel.text.toString())
                    updateBicycle(bicycle)
                }
                "Adding" -> {
                    val bicycle = Bicycle(bicycleId, textInputEditTextBrand.text.toString(), textInputEditTextModel.text.toString())
                    createBicycle(bicycle)
                }
            }
        }

        if(state == "Adding") changeButtonsToAdding()
    }

    private fun updateBicycle(bicycle: Bicycle) {
        val bicycleServiceImpl = BicycleServiceImpl()
        bicycleServiceImpl.updateBicycle(this, bicycle) { ->
            run {
                changeButtonsToShowing(bicycle.id)
                val intent = Intent(this, BicycleListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun createBicycle(bicycle: Bicycle) {
        val bicycleServiceImpl = BicycleServiceImpl()
        bicycleServiceImpl.createBicycle(this, bicycle) { ->
            run {
                changeButtonsToShowing(bicycle.id)
                val intent = Intent(this, BicycleListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun changeButtonsToAdding() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Add Bicycle")
        textInputEditTextBrand.isEnabled = true
        textInputEditTextModel.isEnabled = true
        state = "Adding"
    }

    private fun changeButtonsToShowing(bicycleId: Int){
        buttonDelete.visibility = View.VISIBLE
        buttonDelete.isEnabled = true
        buttonEdit.setText("Edit Bicycle")
        textInputEditTextBrand.isEnabled = false
        textInputEditTextModel.isEnabled = false
        state = "Showing"
    }

    private fun changeButtonsToEditing() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Apply changes")
        textInputEditTextBrand.isEnabled = true
        textInputEditTextModel.isEnabled = true
        state = "Editing"
    }

    private fun getBicycle(bicycleId: Int) {
        val bicycleServiceImpl = BicycleServiceImpl()
        bicycleServiceImpl.getById(this, bicycleId) { response ->
            run {

                val txt_brand: TextInputEditText = findViewById(R.id.TextInputEditTextBrand)
                val txt_model: TextInputEditText = findViewById(R.id.TextInputEditTextModel)
                val img: ImageView = findViewById(R.id.imageViewBicycleDetail)

                txt_brand.setText(response?.brand ?: "")
                txt_model.setText(response?.model ?: "")

                val url = BicycleSingleton.getInstance(this).baseUrl + "/img/bicycle-"
                val imageUrl = url + (response?.id.toString() ?: "0" ) + ".jpg"
                Picasso.with(this).load(imageUrl).into(img);
            }
        }
    }

    private fun deleteBicycle(bicycleId: Int) {
        val bicycleServiceImpl = BicycleServiceImpl()
        bicycleServiceImpl.deleteById(this, bicycleId) { ->
            run {
                val intent = Intent(this, BicycleListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
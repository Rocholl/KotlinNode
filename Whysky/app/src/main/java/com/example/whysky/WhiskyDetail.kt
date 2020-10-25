package com.example.whysky

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.whysky.models.Whisky
import com.example.whysky.service.WhiskyService
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

class WhiskyDetail: AppCompatActivity() {
    private lateinit var state: String
    private lateinit var textInputEditTextNombre: EditText
    private lateinit var textInputEditTextDescripcion: EditText
    private lateinit var textInputEditTextProcedencia: EditText
    private lateinit var textInputEditTextImage: EditText
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.whisky_list_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        state = this.intent.getStringExtra("state").toString()

        val whiskyId = this.intent.getIntExtra("whiskyId", 1)

        textInputEditTextNombre = findViewById(R.id.TextInputEditTextNombre)
        textInputEditTextDescripcion = findViewById(R.id.TextInputEditTextDescripcion)
        textInputEditTextProcedencia = findViewById(R.id.TextInputEditTextProcedencia)
        textInputEditTextImage = findViewById(R.id.TextInputEditTextImage)

        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            deleteWhisky(whiskyId)
        }

        if(state == "Showing") getWhisky(whiskyId)

        buttonEdit = findViewById(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            when(state){
                "Showing" -> {
                    changeButtonsToEditing()
                }
                "Editing" -> {
                    val whisky = Whisky(whiskyId, textInputEditTextNombre.text.toString(), textInputEditTextDescripcion.text.toString(),textInputEditTextProcedencia.text.toString(),textInputEditTextImage.text.toString())

                    updateWhisky(whisky)
                }
                "Adding" -> {
                    val whisky = Whisky(whiskyId, textInputEditTextNombre.text.toString(), textInputEditTextDescripcion.text.toString(),textInputEditTextProcedencia.text.toString(),textInputEditTextImage.text.toString())
                    createWhisky(whisky)
                }
            }
        }

        if(state == "Adding") changeButtonsToAdding()
    }

    private fun updateWhisky(whisky: Whisky) {
        val whiskyService = WhiskyService()
        whiskyService.updateWhisky(this, whisky) { ->
            run {
                changeButtonsToShowing(whisky.id)
                val intent = Intent(this, WhiskyList::class.java)
                startActivity(intent)
            }
        }
    }

    private fun createWhisky(whisky: Whisky) {
        val whiskyService = WhiskyService()
        whiskyService.createWhisky(this, whisky) { ->
            run {
                changeButtonsToShowing(whisky.id)
                val intent = Intent(this, WhiskyList::class.java)
                startActivity(intent)
            }
        }
    }

    private fun changeButtonsToAdding() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Add Whisky")
        textInputEditTextNombre.isEnabled = true
        textInputEditTextDescripcion.isEnabled = true
        textInputEditTextProcedencia.isEnabled = true
        textInputEditTextImage.isEnabled = true
        state = "Adding"
    }

    private fun changeButtonsToShowing(whiskyId: Int){
        buttonDelete.visibility = View.VISIBLE
        buttonDelete.isEnabled = true
        buttonEdit.setText("Edit Whisky")
        textInputEditTextNombre.isEnabled = false
        textInputEditTextDescripcion.isEnabled = false
        textInputEditTextProcedencia.isEnabled = false
        textInputEditTextImage.isEnabled = false
        state = "Showing"
    }

    private fun changeButtonsToEditing() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Apply changes")
        textInputEditTextNombre.isEnabled = true
        textInputEditTextDescripcion.isEnabled = true
        textInputEditTextProcedencia.isEnabled = true
        textInputEditTextImage.isEnabled = true
        state = "Editing"
    }

    private fun getWhisky(whiskyId: Int) {
        val whiskyService = WhiskyService()
        whiskyService.getById(this, whiskyId) { response ->
            run {



                val txt_nombre:    TextInputEditText = findViewById(R.id.TextInputEditTextNombre)
                val txt_descripcion:    TextInputEditText = findViewById(R.id.TextInputEditTextDescripcion)
                val txt_procedencia:    TextInputEditText = findViewById(R.id.TextInputEditTextProcedencia)

                val txt_image:    TextInputEditText = findViewById(R.id.TextInputEditTextImage)

                txt_nombre.setText(response?.nombre ?: "")
                txt_descripcion.setText(response?.descripcion ?: "")
                txt_procedencia.setText(response?.procedencia ?: "")
                txt_image.setText(response?.image ?: "")



            }
        }
    }

    private fun deleteWhisky(whiskyId: Int) {
        val whiskyService = WhiskyService()
        whiskyService.deleteById(this, whiskyId) { ->
            run {
                val intent = Intent(this, WhiskyList::class.java)
                startActivity(intent)
            }
        }
    }
}
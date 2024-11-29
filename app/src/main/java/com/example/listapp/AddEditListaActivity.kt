package com.example.listapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Spinner


class AddEditListaActivity : AppCompatActivity() {

    private lateinit var editNombre: EditText
    private lateinit var editFecha: EditText
    private lateinit var spinnerPrioridad: Spinner
    private lateinit var editDescripcion: EditText
    private lateinit var imageView: ImageView
    private lateinit var btnSelectImage: Button
    private lateinit var btnSave: Button
    private var selectedImageUri: Uri? = null  // Inicializa como null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_lista)

        editNombre = findViewById(R.id.editNombre)
        editFecha = findViewById(R.id.editFecha)
        spinnerPrioridad = findViewById(R.id.spinnerPrioridad)
        editDescripcion = findViewById(R.id.editDescripcion)
        imageView = findViewById(R.id.imageView)
        btnSelectImage = findViewById(R.id.btnSelectImage)
        btnSave = findViewById(R.id.btnSave)

        // Configura el Spinner de prioridad
        val priorities = arrayOf("Alta", "Media", "Baja")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPrioridad.adapter = adapter

        // Configurar el botón para seleccionar una imagen
        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }

        // Configurar el botón de guardar
        btnSave.setOnClickListener {
            val nombre = editNombre.text.toString()
            val fecha = editFecha.text.toString()
            val prioridad = spinnerPrioridad.selectedItem.toString()
            val descripcion = editDescripcion.text.toString()
            val imagenUri = selectedImageUri?.toString()

            val lista = ListaEntity(
                nombre = nombre,
                fecha = fecha,
                prioridad = prioridad,
                descripcion = descripcion,
                imagenUri = imagenUri
            )

            // Guardar la lista
            val viewModel: ListaViewModel by viewModels()
            viewModel.insert(lista)

            finish()  // Regresar a la pantalla principal
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            selectedImageUri = data?.data
            imageView.setImageURI(selectedImageUri)  // Muestra la imagen seleccionada
        }
    }

    companion object {
        const val IMAGE_PICK_CODE = 1000
    }
}
package com.example.listapp
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class AddEditListaActivity : AppCompatActivity() {

    private lateinit var editNombre: EditText
    private lateinit var editFecha: EditText
    private lateinit var spinnerPrioridad: Spinner
    private lateinit var editDescripcion: EditText
    private lateinit var imageView: ImageView
    private lateinit var btnSelectImage: Button
    private lateinit var btnSave: Button
    private var selectedImageUri: Uri? = null
    private var listaId: Int? = null

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

        val priorities = arrayOf("Alta", "Media", "Baja")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPrioridad.adapter = adapter

        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }

        listaId = intent.getIntExtra("LISTA_ID", -1)
        if (listaId != -1) {
            val viewModel: ListaViewModel by viewModels()
            viewModel.allListas.observe(this) { listas ->
                val lista = listas.find { it.id == listaId }
                lista?.let {
                    editNombre.setText(it.nombre)
                    editFecha.setText(it.fecha)
                    spinnerPrioridad.setSelection(priorities.indexOf(it.prioridad))
                    editDescripcion.setText(it.descripcion)
                    selectedImageUri = it.imagenUri?.let { uri -> Uri.parse(uri) }
                    imageView.setImageURI(selectedImageUri)
                }
            }
        }

        editFecha.setOnClickListener {
            showDatePickerDialog()
        }

        btnSave.setOnClickListener {
            val nombre = editNombre.text.toString()
            val fecha = editFecha.text.toString()
            val prioridad = spinnerPrioridad.selectedItem.toString()
            val descripcion = editDescripcion.text.toString()
            val imagenUri = selectedImageUri?.toString()

            val lista = ListaEntity(
                id = if (listaId == -1) 0 else listaId!!,
                nombre = nombre,
                fecha = fecha,
                prioridad = prioridad,
                descripcion = descripcion,
                imagenUri = imagenUri
            )

            val viewModel: ListaViewModel by viewModels()
            if (listaId == -1) {
                viewModel.insert(lista)
            } else {
                viewModel.update(lista)
            }

            finish()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            editFecha.setText(selectedDate)
        }, year, month, day)

        // Establecer la fecha mínima en el DatePickerDialog
        datePickerDialog.datePicker.minDate = calendar.timeInMillis

        datePickerDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            selectedImageUri = data?.data
            imageView.setImageURI(selectedImageUri)
        }
    }

    companion object {
        const val IMAGE_PICK_CODE = 1000
    }
}
package com.example.listapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddEditItemActivity : AppCompatActivity() {
    // En tu actividad o fragmento donde agregas los ítems
    private lateinit var editItemNombre: EditText
    private lateinit var editItemDescripcion: EditText
    private lateinit var btnAddItem: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_item)

        editItemNombre = findViewById(R.id.editItemNombre)
        editItemDescripcion = findViewById(R.id.editItemDescripcion)
        btnAddItem = findViewById(R.id.btnAddItem)

        btnAddItem.setOnClickListener {
            val nombre = editItemNombre.text.toString()
            val descripcion = editItemDescripcion.text.toString()

            if (nombre.isNotBlank() && descripcion.isNotBlank()) {
                val item = ItemEntity(
                    listaId = 1, // Suponiendo que es la lista con ID 1
                    nombre = nombre,
                    descripcion = descripcion
                )

                // Obtener el ViewModel y agregar el ítem
                val itemViewModel: ItemViewModel by viewModels()
                itemViewModel.insert(item)

                // Regresar a la pantalla anterior o limpiar los campos
                finish() // o limpiar los campos editables
            }
        }
    }

}
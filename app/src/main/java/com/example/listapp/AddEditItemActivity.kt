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

    private lateinit var editItemNombre: EditText
    private lateinit var editItemDescripcion: EditText
    private lateinit var btnAddItem: Button
    private var itemId: Int? = null
    private var listaId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_item)

        editItemNombre = findViewById(R.id.editItemNombre)
        editItemDescripcion = findViewById(R.id.editItemDescripcion)
        btnAddItem = findViewById(R.id.btnAddItem)

        listaId = intent.getIntExtra("LISTA_ID", 0)
        itemId = intent.getIntExtra("ITEM_ID", -1)

        val itemViewModel: ItemViewModel by viewModels()

        if (itemId != -1) {
            itemViewModel.getItemsByLista(listaId).observe(this) { items ->
                val item = items.find { it.id == itemId }
                item?.let {
                    editItemNombre.setText(it.nombre)
                    editItemDescripcion.setText(it.descripcion)
                }
            }
        }

        btnAddItem.setOnClickListener {
            val nombre = editItemNombre.text.toString()
            val descripcion = editItemDescripcion.text.toString()

            if (nombre.isNotBlank() && descripcion.isNotBlank()) {
                val item = ItemEntity(
                    id = if (itemId == -1) 0 else itemId!!,
                    listaId = listaId,
                    nombre = nombre,
                    descripcion = descripcion
                )

                if (itemId == -1) {
                    itemViewModel.insert(item)
                } else {
                    itemViewModel.update(item)
                }

                finish()
            }
        }
    }
}
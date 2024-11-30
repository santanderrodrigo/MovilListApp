package com.example.listapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ViewItemsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var textNoItems: TextView
    private lateinit var textTituloLista: TextView
    private lateinit var textDescripcionLista: TextView
    private val itemViewModel: ItemViewModel by viewModels()
    private val listaViewModel: ListaViewModel by viewModels()
    private var listaId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_items)

        listaId = intent.getIntExtra("LISTA_ID", 0)

        recyclerView = findViewById(R.id.recyclerView)
        textNoItems = findViewById(R.id.textNoItems)
        textTituloLista = findViewById(R.id.textTituloLista)
        textDescripcionLista = findViewById(R.id.textDescripcionLista)
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemAdapter = ItemAdapter(emptyList(), { item ->
            val intent = Intent(this, AddEditItemActivity::class.java)
            intent.putExtra("ITEM_ID", item.id)
            intent.putExtra("LISTA_ID", listaId)
            startActivity(intent)
        }, { item ->
            showDeleteConfirmationDialog(item)
        })
        recyclerView.adapter = itemAdapter

        itemViewModel.getItemsByLista(listaId).observe(this) { items ->
            itemAdapter.setItems(items)
            textNoItems.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }

        listaViewModel.allListas.observe(this) { listas ->
            val lista = listas.find { it.id == listaId }
            lista?.let {
                textTituloLista.text = it.nombre
                textDescripcionLista.text = it.descripcion
            }
        }

        findViewById<FloatingActionButton>(R.id.fabAddItem).setOnClickListener {
            val intent = Intent(this, AddEditItemActivity::class.java)
            intent.putExtra("LISTA_ID", listaId)
            startActivity(intent)
        }
    }

    private fun showDeleteConfirmationDialog(item: ItemEntity) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Ítem")
            .setMessage("¿Estás seguro de que deseas eliminar este ítem?")
            .setPositiveButton("Sí") { dialog, _ ->
                deleteItem(item)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun deleteItem(item: ItemEntity) {
        itemViewModel.delete(item)
        Toast.makeText(this, "Ítem eliminado con éxito", Toast.LENGTH_SHORT).show()
    }
}
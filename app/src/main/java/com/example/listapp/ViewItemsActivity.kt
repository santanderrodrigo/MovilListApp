package com.example.listapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager


class ViewItemsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter
    private val itemViewModel: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_items)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemAdapter = ItemAdapter(emptyList()) // Inicializa con lista vacía
        recyclerView.adapter = itemAdapter

        // Suponiendo que la lista tiene ID 1, recuperamos los ítems asociados a esa lista
        itemViewModel.getItemsByLista(1)

        // Observamos los ítems de la lista
        itemViewModel.itemsByLista.observe(this) { items ->
            itemAdapter.setItems(items)  // Actualizamos los ítems en el RecyclerView
        }
    }
}

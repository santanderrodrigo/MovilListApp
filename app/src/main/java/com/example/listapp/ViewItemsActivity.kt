package com.example.listapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ViewItemsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter
    private val itemViewModel: ItemViewModel by viewModels()
    private var listaId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_items)

        listaId = intent.getIntExtra("LISTA_ID", 0)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemAdapter = ItemAdapter(emptyList()) { item ->
            val intent = Intent(this, AddEditItemActivity::class.java)
            intent.putExtra("ITEM_ID", item.id)
            startActivity(intent)
        }
        recyclerView.adapter = itemAdapter

        itemViewModel.getItemsByLista(listaId)

        itemViewModel.itemsByLista.observe(this) { items ->
            itemAdapter.setItems(items)
        }

        findViewById<FloatingActionButton>(R.id.fabAddItem).setOnClickListener {
            val intent = Intent(this, AddEditItemActivity::class.java)
            intent.putExtra("LISTA_ID", listaId)
            startActivity(intent)
        }
    }
}
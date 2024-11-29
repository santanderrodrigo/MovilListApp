package com.example.listapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.RecyclerView // No olvides importar RecyclerView
import com.example.listapp.ListaViewModel
import com.example.listapp.ListaAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private val viewModel: ListaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        fabAdd = findViewById(R.id.fabAdd)

        // Configura el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ListaAdapter(emptyList())  // Inicializa con una lista vacía
        recyclerView.adapter = adapter

        // Observa los cambios en el ViewModel
        viewModel.allListas.observe(this) { listas ->
            adapter.setList(listas) // Actualiza la lista con el nuevo contenido
        }

        // Configura el FloatingActionButton
        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddEditListaActivity::class.java))
        }
    }
}

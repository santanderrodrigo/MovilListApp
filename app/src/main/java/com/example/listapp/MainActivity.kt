package com.example.listapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private val viewModel: ListaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        fabAdd = findViewById(R.id.fabAdd)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ListaAdapter(emptyList()) { lista ->
            val intent = Intent(this, ViewItemsActivity::class.java)
            intent.putExtra("LISTA_ID", lista.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        viewModel.allListas.observe(this) { listas ->
            adapter.setList(listas)
        }

        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddEditListaActivity::class.java))
        }
    }
}
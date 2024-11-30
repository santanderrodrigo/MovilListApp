package com.example.listapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var textNoListas: TextView
    private val viewModel: ListaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        fabAdd = findViewById(R.id.fabAdd)
        textNoListas = findViewById(R.id.textNoListas)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ListaAdapter(emptyList(), { lista ->
            val intent = Intent(this, ViewItemsActivity::class.java)
            intent.putExtra("LISTA_ID", lista.id)
            startActivity(intent)
        }, { lista ->
            val intent = Intent(this, AddEditListaActivity::class.java)
            intent.putExtra("LISTA_ID", lista.id)
            startActivity(intent)
        }, { lista ->
            showDeleteConfirmationDialog(lista)
        })
        recyclerView.adapter = adapter

        viewModel.allListas.observe(this) { listas ->
            adapter.setList(listas)
            textNoListas.visibility = if (listas.isEmpty()) View.VISIBLE else View.GONE
        }

        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddEditListaActivity::class.java))
        }
    }

    private fun showDeleteConfirmationDialog(lista: ListaEntity) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Lista")
            .setMessage("¿Estás seguro de que deseas eliminar esta lista?")
            .setPositiveButton("Sí") { dialog, _ ->
                deleteLista(lista)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun deleteLista(lista: ListaEntity) {
        viewModel.delete(lista)
        Toast.makeText(this, "Lista eliminada con éxito", Toast.LENGTH_SHORT).show()
    }
}
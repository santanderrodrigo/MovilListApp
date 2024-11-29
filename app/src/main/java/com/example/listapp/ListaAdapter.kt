package com.example.listapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListaAdapter(private var listas: List<ListaEntity>) : RecyclerView.Adapter<ListaAdapter.ListaViewHolder>() {

    // Método para actualizar la lista de datos
    fun setList(newList: List<ListaEntity>) {
        listas = newList
        notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)
        return ListaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        val lista = listas[position]
        holder.nombre.text = lista.nombre
        holder.fecha.text = lista.fecha
    }

    override fun getItemCount(): Int = listas.size

    class ListaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.textNombre)
        val fecha: TextView = itemView.findViewById(R.id.textFecha)
    }
}


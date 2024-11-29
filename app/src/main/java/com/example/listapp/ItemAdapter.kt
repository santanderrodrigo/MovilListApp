package com.example.listapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private var items: List<ItemEntity>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // Método para actualizar los ítems
    fun setItems(newItems: List<ItemEntity>) {
        items = newItems
        notifyDataSetChanged()  // Notifica al adaptador que los datos cambiaron
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.nombre.text = item.nombre
        holder.descripcion.text = item.descripcion
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.textNombre)
        val descripcion: TextView = itemView.findViewById(R.id.textDescripcion)
    }
}

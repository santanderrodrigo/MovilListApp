package com.example.listapp

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ListaAdapter(
    private var listas: List<ListaEntity>,
    private val onItemClick: (ListaEntity) -> Unit,
    private val onEditClick: (ListaEntity) -> Unit,
    private val onDeleteClick: (ListaEntity) -> Unit
) : RecyclerView.Adapter<ListaAdapter.ListaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)
        return ListaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        val lista = listas[position]
        holder.nombre.text = lista.nombre
        holder.fecha.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(lista.fecha))
        holder.prioridad.text = lista.prioridad
        holder.itemView.setOnClickListener {
            onItemClick(lista)
        }
        holder.itemView.setOnLongClickListener {
            showPopupMenu(holder.itemView, lista)
            true
        }

        if (lista.imagenUri != null) {
            holder.imagen.visibility = View.VISIBLE
            holder.imagen.setImageURI(Uri.parse(lista.imagenUri))
        } else {
            holder.imagen.visibility = View.GONE
        }

        // Set color based on priority
        when (lista.prioridad) {
            "Alta" -> holder.prioridad.setTextColor(holder.itemView.context.getColor(android.R.color.holo_red_dark))
            "Media" -> holder.prioridad.setTextColor(holder.itemView.context.getColor(android.R.color.holo_orange_dark))
            "Baja" -> holder.prioridad.setTextColor(holder.itemView.context.getColor(android.R.color.holo_green_dark))
        }
    }

    override fun getItemCount(): Int = listas.size

    fun setList(newList: List<ListaEntity>) {
        listas = newList
        notifyDataSetChanged()
    }

    private fun showPopupMenu(view: View, lista: ListaEntity) {
        val popupMenu = PopupMenu(view.context, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_lista_contextual, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_edit -> {
                    onEditClick(lista)
                    true
                }
                R.id.action_delete -> {
                    onDeleteClick(lista)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    class ListaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.textNombre)
        val fecha: TextView = itemView.findViewById(R.id.textFecha)
        val prioridad: TextView = itemView.findViewById(R.id.textPrioridad)
        val imagen: ImageView = itemView.findViewById(R.id.imageLista)
    }
}
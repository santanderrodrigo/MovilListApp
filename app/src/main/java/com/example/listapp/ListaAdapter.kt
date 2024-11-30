package com.example.listapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListaAdapter(
    private var listas: List<ListaEntity>,
    private val onItemClick: (ListaEntity) -> Unit
) : RecyclerView.Adapter<ListaAdapter.ListaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)
        return ListaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        val lista = listas[position]
        holder.nombre.text = lista.nombre
        holder.fecha.text = lista.fecha
        holder.itemView.setOnClickListener {
            onItemClick(lista)
        }
        holder.itemView.setOnLongClickListener {
            showPopupMenu(holder.itemView, lista)
            true
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
        inflater.inflate(R.menu.menu_lista, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_edit -> {
                    val intent = Intent(view.context, AddEditListaActivity::class.java)
                    intent.putExtra("LISTA_ID", lista.id)
                    view.context.startActivity(intent)
                    true
                }
                R.id.action_delete -> {
                    // Implementar la lógica para eliminar la lista
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
    }
}
package com.example.listapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listas")
data class ListaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val fecha: String,
    val prioridad: String,
    val descripcion: String,
    val imagenUri: String? = null // Ruta opcional para la imagen
)

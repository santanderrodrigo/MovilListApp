package com.example.listapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listas")
data class ListaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val fecha: Long,
    val prioridad: String,
    val descripcion: String,
    val imagenUri: String? = null
)
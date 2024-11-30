package com.example.listapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val listaId: Int,
    val nombre: String,
    val descripcion: String
)

package com.example.listapp

import androidx.lifecycle.LiveData

class ListaRepository(private val listaDao: ListaDao) {
    val allListas: LiveData<List<ListaEntity>> = listaDao.getAllListas()

    suspend fun insert(lista: ListaEntity) {
        listaDao.insert(lista)
    }

    suspend fun update(lista: ListaEntity) {
        listaDao.update(lista)
    }

    suspend fun delete(lista: ListaEntity) {
        listaDao.delete(lista)
    }
}

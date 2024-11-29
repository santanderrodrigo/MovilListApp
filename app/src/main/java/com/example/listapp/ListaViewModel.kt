package com.example.listapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ListaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ListaRepository
    val allListas: LiveData<List<ListaEntity>>

    init {
        val dao = AppDatabase.getDatabase(application).listaDao()
        repository = ListaRepository(dao)
        allListas = repository.allListas
    }

    fun insert(lista: ListaEntity) = viewModelScope.launch {
        repository.insert(lista)
    }

    fun update(lista: ListaEntity) = viewModelScope.launch {
        repository.update(lista)
    }

    fun delete(lista: ListaEntity) = viewModelScope.launch {
        repository.delete(lista)
    }
}

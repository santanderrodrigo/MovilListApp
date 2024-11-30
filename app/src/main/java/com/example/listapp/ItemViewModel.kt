package com.example.listapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.listapp.AppDatabase
import com.example.listapp.ItemEntity
import com.example.listapp.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ItemRepository
    val itemsByLista: MutableLiveData<List<ItemEntity>> = MutableLiveData()

    init {
        val itemDao = AppDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)
    }

    fun getItemsByLista(listaId: Int) {
        repository.getItemsByLista(listaId).observeForever {
            itemsByLista.value = it
        }
    }

    fun insert(item: ItemEntity) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: ItemEntity) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(item: ItemEntity) = viewModelScope.launch {
        repository.delete(item)
    }
}
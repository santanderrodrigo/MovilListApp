package com.example.listapp

import androidx.lifecycle.LiveData

class ItemRepository(private val itemDao: ItemDao) {
    fun getItemsByLista(listaId: Int): LiveData<List<ItemEntity>> = itemDao.getItemsByLista(listaId)

    suspend fun insert(item: ItemEntity) {
        itemDao.insert(item)
    }

    suspend fun update(item: ItemEntity) {
        itemDao.update(item)
    }

    suspend fun delete(item: ItemEntity) {
        itemDao.delete(item)
    }
}
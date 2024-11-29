package com.example.listapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(item: ItemEntity)

    @Query("SELECT * FROM items WHERE listaId = :listaId")
    fun getItemsByLista(listaId: Int): LiveData<List<ItemEntity>>

    @Delete
    suspend fun delete(item: ItemEntity)
}

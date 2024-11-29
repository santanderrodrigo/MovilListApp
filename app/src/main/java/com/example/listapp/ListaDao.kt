package com.example.listapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lista: ListaEntity)

    @Update
    suspend fun update(lista: ListaEntity)

    @Delete
    suspend fun delete(lista: ListaEntity)

    @Query("SELECT * FROM listas")
    fun getAllListas(): LiveData<List<ListaEntity>>
}

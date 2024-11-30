package com.example.listapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ListaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lista: ListaEntity)

    @Update
    suspend fun update(lista: ListaEntity)

    @Delete
    suspend fun delete(lista: ListaEntity)

    @Query("SELECT * FROM listas ORDER BY fecha ASC, CASE prioridad WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 WHEN 'Baja' THEN 3 END ASC")
    fun getAllListas(): LiveData<List<ListaEntity>>
}
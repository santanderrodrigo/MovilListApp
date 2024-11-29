package com.example.listapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ListaEntity::class, ItemEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listaDao(): ListaDao
    abstract fun itemDao(): ItemDao  // Agrega este DAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lista_database"
                )
                    .fallbackToDestructiveMigration()  // Borrar y recrear la base de datos cuando hay una migración fallida
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

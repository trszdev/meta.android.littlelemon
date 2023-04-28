package com.meta.littlelemon.util

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Query("SELECT * FROM MenuItem")
    fun getAllMenuItems(): Flow<List<MenuItem>>


    @Insert
    fun save(menuItems: List<MenuItem>)


    @Delete
    fun delete(menuItems: List<MenuItem>)
}

@Database(entities = [MenuItem::class], version = 1)
abstract class MenuDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao
}
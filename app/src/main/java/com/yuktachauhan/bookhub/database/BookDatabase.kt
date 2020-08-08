package com.yuktachauhan.bookhub.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class],version = 1)
abstract class BookDatabase :RoomDatabase() {
    //this fun will return a book dao object on which we can use dao method
    abstract fun bookDao():BookDao
}
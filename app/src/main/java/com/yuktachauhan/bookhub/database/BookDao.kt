package com.yuktachauhan.bookhub.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Insert
    fun insertBook(bookEntity:BookEntity)

    @Delete
    fun deleteBook(bookEntity: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBooks():List<BookEntity>

    //:bookId = colon before fun parameter because we want to tell that book id will come from function written just below
    @Query("SELECT * FROM books WHERE book_id = :bookId")
    fun getBookById(bookId:String) : BookEntity

}
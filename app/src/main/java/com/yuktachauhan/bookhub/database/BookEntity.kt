package com.yuktachauhan.bookhub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//if we want multiples tables then we use multiple entity classes
//by default the name of table is classname
@Entity(tableName = "books")
data class BookEntity (
    @PrimaryKey val book_id:Int,
    @ColumnInfo(name = "book_name") val bookName:String,
    @ColumnInfo(name = "book_author") val bookAuthor:String,
    @ColumnInfo(name = "book_rating") val bookRating:String,
    @ColumnInfo(name = "book_price") val bookPrice:String,
    @ColumnInfo(name = "book_image") val bookImage:String,
    @ColumnInfo(name = "book_desc") val bookDesc:String
)
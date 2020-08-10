package com.yuktachauhan.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yuktachauhan.bookhub.R
import com.yuktachauhan.bookhub.database.BookEntity
import org.w3c.dom.Text

class FavRecyclerAdapter(val context:Context,val bookList:List<BookEntity>) : RecyclerView.Adapter<FavRecyclerAdapter.FavViewHolder>(){

    class FavViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val imgFavBookImage:ImageView = view.findViewById(R.id.imgFavBookImage)
        val txtFavBookTitle:TextView=view.findViewById(R.id.txtFavBookTitle)
        val txtFavBookAuthor:TextView=view.findViewById(R.id.txtFavBookAuthor)
        val txtFavBookPrice:TextView=view.findViewById(R.id.txtFavBookPrice)
        val txtFavBookRating:TextView=view.findViewById(R.id.txtFavBookRating)
        val llContent:LinearLayout=view.findViewById(R.id.llFavContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {

        val view =LayoutInflater.from(parent.context).inflate(R.layout.recycler_favorite_single_row,parent,false)
        return FavViewHolder(view)

    }

    override fun getItemCount(): Int {

        return bookList.size
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {

        val book=bookList[position]
        holder.txtFavBookTitle.text=book.bookName
        holder.txtFavBookAuthor.text=book.bookAuthor
        holder.txtFavBookPrice.text=book.bookPrice
        holder.txtFavBookRating.text=book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imgFavBookImage)
    }
}
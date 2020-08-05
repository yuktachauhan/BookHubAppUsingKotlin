package com.yuktachauhan.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.yuktachauhan.bookhub.R
import com.yuktachauhan.bookhub.model.Book

class RecyclerDashboardAdapter(val context : Context,val bookList:ArrayList<Book>)
    : RecyclerView.Adapter<RecyclerDashboardAdapter.RecyclerDashBoardViewHolder>(){

    class RecyclerDashBoardViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val bookName:TextView=view.findViewById(R.id.txtBookName)
        val bookAuthor:TextView=view.findViewById(R.id.txtBookAuthor)
        val bookCost:TextView=view.findViewById(R.id.txtBookPrice)
        val bookRatings:TextView=view.findViewById(R.id.txtBookRating)
        val bookImage:ImageView=view.findViewById(R.id.imgBookImage)
            //this is for linear layout(parent layout of all these views) ,as we want to make all views clickable
        val llcontent:LinearLayout=view.findViewById(R.id.llContent)
    }

    //this method is responsible for creating initial view that recyclerview will hold
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerDashBoardViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row_item,parent,false)
        return RecyclerDashBoardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    //responsible for recycling and reusing of view holder
    override fun onBindViewHolder(holder: RecyclerDashBoardViewHolder, position: Int) {
        val book=bookList[position]
        holder.bookName.text=book.bookName
        holder.bookAuthor.text=book.bookAuthor
        holder.bookCost.text=book.bookCost
        holder.bookRatings.text=book.bookRatings
        holder.bookImage.setImageResource(book.bookImage)
        holder.llcontent.setOnClickListener {
            Toast.makeText(context," Clicked on ${book.bookName}",Toast.LENGTH_SHORT).show()
        }
    }

}
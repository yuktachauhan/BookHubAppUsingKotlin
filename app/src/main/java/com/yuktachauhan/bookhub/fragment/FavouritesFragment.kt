package com.yuktachauhan.bookhub.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.yuktachauhan.bookhub.R
import com.yuktachauhan.bookhub.adapter.FavRecyclerAdapter
import com.yuktachauhan.bookhub.database.BookDatabase
import com.yuktachauhan.bookhub.database.BookEntity

class FavouritesFragment : Fragment() {

    lateinit var recyclerFav:RecyclerView
    lateinit var progressLayout:RelativeLayout
    lateinit var progressBar:ProgressBar
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var recyclerAdapter:FavRecyclerAdapter

    var dbBookList = listOf<BookEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_favourites, container, false)
        recyclerFav=view.findViewById(R.id.recyclerFav)
        progressLayout=view.findViewById(R.id.progressLayoutFav)
        progressLayout.visibility=View.VISIBLE
        progressBar=view.findViewById(R.id.progressBarFav)
        progressBar.visibility=View.VISIBLE
        //two items in a row
        layoutManager=GridLayoutManager(activity as Context,2 )
        //filling booklist from database
        dbBookList=RetrieveFavourites(activity as Context).execute().get()
        if(dbBookList!=null && activity!=null){
            progressLayout.visibility=View.GONE

            recyclerAdapter=FavRecyclerAdapter(activity as Context,dbBookList)
            recyclerFav.adapter=recyclerAdapter
            recyclerFav.layoutManager=layoutManager
        }
        return view
    }

    //Async task class to retrieve the favourite books from the database
    class RetrieveFavourites(val context: Context):AsyncTask<Void,Void,List<BookEntity>>(){
        override fun doInBackground(vararg p0: Void?): List<BookEntity> {
           val db = Room.databaseBuilder(context,BookDatabase::class.java,"books_db").build()
            return db.bookDao().getAllBooks()
        }

    }
}
package com.yuktachauhan.bookhub.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.yuktachauhan.bookhub.R
import com.yuktachauhan.bookhub.adapter.RecyclerDashboardAdapter
import com.yuktachauhan.bookhub.model.Book
import com.yuktachauhan.bookhub.util.ConnectionManager
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class DashBoardFragment : Fragment() {

    lateinit var recyclerDashboard:RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    //lateinit var btnCheckInternet:Button
    lateinit var progressBarLayout:RelativeLayout
    lateinit var progressBar:ProgressBar

    var booKInfoList= ArrayList<Book>()
//    val bookInfoList = arrayListOf<Book>(
//        Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
//        Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
//        Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
//        Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
//        Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
//        Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
//        Book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
//        Book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
//        Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
//        Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)
//    )



    lateinit var recyclerAdapter:RecyclerDashboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //false - because we don;t want to attach this fragment permanently to the activity,we will change
        val view = inflater.inflate(R.layout.fragment_dash_board, container, false)

        //progress bar visible at the time fragment is loaded
        progressBarLayout=view.findViewById(R.id.progressBarLayout)
        progressBar=view.findViewById(R.id.progressBar)
        progressBarLayout.visibility=View.VISIBLE

//        //checking connectivity of the device to the internet
//        btnCheckInternet=view.findViewById(R.id.btnCheckInternet)
//        btnCheckInternet.setOnClickListener {
//            if(ConnectionManager().checkConnectivity(activity as Context)){
//                //if connected
//                val dialog=AlertDialog.Builder(activity as Context)
//                dialog.setTitle("Success")
//                dialog.setMessage("Internet Connection found")
//                dialog.setPositiveButton("Ok"){
//                    text,listener->
//                    //do nothing
//                }
//                dialog.setNegativeButton("Cancel"){
//                        text,listener->
//                    //do nothing
//                }
//                dialog.create()
//                dialog.show()
//            }else{
//                //if not connected
//                val dialog=AlertDialog.Builder(activity as Context)
//                dialog.setTitle("Error!")
//                dialog.setMessage("No Internet Connection found")
//                dialog.setPositiveButton("Ok"){
//                        text,listener->
//                    //do nothing
//                }
//                dialog.setNegativeButton("Cancel"){
//                        text,listener->
//                    //do nothing
//                }
//                dialog.create()
//                dialog.show()
//            }
//        }
        recyclerDashboard=view.findViewById(R.id.recyclerDashboard)
        layoutManager=LinearLayoutManager(activity)

        val queue= Volley.newRequestQueue(activity as Context)
        //whenever we use http request we have to add network security config (xml directory and manifest)
        val url = "http://13.235.250.119/v1/book/fetch_books/"
        if(ConnectionManager().checkConnectivity(activity as Context)){
            //making request only if there is a connection
            val jsonObjectRequest= object : JsonObjectRequest(Method.GET,url,null, Response.Listener {

//            //to print response in logcat
//            println("Response is $it")

                try {
                    //hide progress bar if we are connected to the internet
                    progressBarLayout.visibility=View.GONE
                    //to handle json exception
                    val success = it.getBoolean("success")
                    //if success variable in json response is true
                    if(success){
                        val data=it.getJSONArray("data")
                        for(i in 0 until data.length()) {
                            //getting each object from the json array and passing values to make book object
                            val bookJsonObject=data.getJSONObject(i)
                            val bookObject = Book(
                                bookJsonObject.getString("book_id"),
                                bookJsonObject.getString("name"),
                                bookJsonObject.getString("author"),
                                bookJsonObject.getString("rating"),
                                bookJsonObject.getString("price"),
                                bookJsonObject.getString("image")
                            )
                            booKInfoList.add(bookObject)
                            recyclerAdapter= RecyclerDashboardAdapter(activity as Context,booKInfoList)
                            recyclerDashboard.adapter=recyclerAdapter
                            recyclerDashboard.layoutManager=layoutManager
                            //to add divider line in recycler view
//                            recyclerDashboard.addItemDecoration(DividerItemDecoration(
//                                recyclerDashboard.context,(layoutManager as LinearLayoutManager).orientation)
//                            )
                        }
                    }else{
                        //when success is false
                        Toast.makeText(activity as Context,"Some error occurred ",Toast.LENGTH_SHORT).show()
                    }
                }catch (e:JSONException){
                    Toast.makeText(activity as Context,"Some unexpected error occurred ",Toast.LENGTH_SHORT).show()
                }

            },Response.ErrorListener {

                //this block handles volley errors
                Toast.makeText(activity as Context,"Some volley error occurred ",Toast.LENGTH_SHORT).show()
//                //to print error in logcat
//                println("Error is $it")
            }){

                override fun getHeaders(): MutableMap<String, String> {
                    val headers=HashMap<String , String>()
                    headers["Content-type"]="application/json"
                    headers["token"]="e640cef7399dd1"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        }else{
            //if no internet connection
            val dialog=AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error!")
            dialog.setMessage("No Internet Connection found")
            dialog.setPositiveButton("open settings"){
                //opening settings to open data by the user
                //we will use implicit intent - use to open things that are outside the app
                    text,listener->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                //to start fragment again and recreate the list
                activity?.finish()

            }
            dialog.setNegativeButton("Exit"){
                    text,listener->
                //exit the app - app closed completely
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }

        return view
    }

}
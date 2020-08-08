package com.yuktachauhan.bookhub.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.yuktachauhan.bookhub.R
import com.yuktachauhan.bookhub.util.ConnectionManager
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {
    lateinit var imgBookImage:ImageView
    lateinit var txtBookName:TextView
    lateinit var txtBookAuthor:TextView
    lateinit var txtBookPrice:TextView
    lateinit var txtBookRating:TextView
    lateinit var txtBookDesc:TextView
    lateinit var btnAddToFav:Button
    lateinit var progressBarLayout:RelativeLayout
    lateinit var progressBar:ProgressBar

    lateinit var toolbar: Toolbar

    //default value
    var bookId:String?="-1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        imgBookImage=findViewById(R.id.imgBookImage)
        txtBookName=findViewById(R.id.txtBookName)
        txtBookAuthor=findViewById(R.id.txtBookAuthor)
        txtBookPrice=findViewById(R.id.txtBookPrice)
        txtBookRating=findViewById(R.id.txtBookRating)
        txtBookDesc=findViewById(R.id.txtBookDesc)
        btnAddToFav=findViewById(R.id.btnAddToFav)
        progressBarLayout=findViewById(R.id.progressBarLayout)
        progressBarLayout.visibility= View.VISIBLE
        progressBar=findViewById(R.id.progressBar)
        progressBar.visibility= View.VISIBLE

        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title="Book Details"

        if(intent!=null){
            bookId=intent.getStringExtra("book_id")
        }else{
            finish()
            Toast.makeText(this@DescriptionActivity,"Some unexpected error occurred",Toast.LENGTH_SHORT).show()
        }
        if(bookId=="-1"){
            finish()
            Toast.makeText(this@DescriptionActivity,"Some unexpected error occurred",Toast.LENGTH_SHORT).show()
        }

        val queue=Volley.newRequestQueue(this@DescriptionActivity)
        val url="http://13.235.250.119/v1/book/get_book/"
        val jsonParams=JSONObject()
        jsonParams.put("book_id",bookId)
        if(ConnectionManager().checkConnectivity(this@DescriptionActivity)){
            val jsonRequest= object : JsonObjectRequest(Method.POST,url,jsonParams,com.android.volley.Response.Listener{

                try {

                    val success=it.getBoolean("success")
                    if(success){

                        val bookJsonObject = it.getJSONObject("book_data")
                        progressBarLayout.visibility=View.GONE
                        Picasso.get().load(bookJsonObject.getString("image")).error(R.drawable.default_book_cover).into(imgBookImage)
                        txtBookName.text=bookJsonObject.getString("name")
                        txtBookAuthor.text=bookJsonObject.getString("author")
                        txtBookRating.text=bookJsonObject.getString("rating")
                        txtBookPrice.text=bookJsonObject.getString("price")
                        txtBookDesc.text=bookJsonObject.getString("description")
                    }else{
                        //when success is false
                        Toast.makeText(this@DescriptionActivity,"Some error occurred ",Toast.LENGTH_SHORT).show()
                    }
                }catch (e:JSONException){

                    Toast.makeText(this@DescriptionActivity,"Some error occurred ",Toast.LENGTH_SHORT).show()
                }

            },com.android.volley.Response.ErrorListener{
                Toast.makeText(this@DescriptionActivity,"Some Volley error occurred $it",Toast.LENGTH_SHORT).show()
            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers=HashMap<String , String>()
                    headers["Content-type"]="application/json"
                    headers["token"]="e640cef7399dd1"
                    return headers
                }
            }

            queue.add(jsonRequest)
        }else{
            //if no internet connection
            val dialog= AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Error!")
            dialog.setMessage("No Internet Connection found")
            dialog.setPositiveButton("open settings"){
                //opening settings to open data by the user
                //we will use implicit intent - use to open things that are outside the app
                    text,listener->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                //to start fragment again and recreate the list
                finish()

            }
            dialog.setNegativeButton("Exit"){
                    text,listener->
                //exit the app - app closed completely
                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }
            dialog.create()
            dialog.show()
        }

    }
}
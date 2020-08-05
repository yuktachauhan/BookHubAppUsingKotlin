package com.yuktachauhan.bookhub.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

//this class is used to check whether the device has internet connection or not
class ConnectionManager {

    fun checkConnectivity(context : Context):Boolean{
        //tells us about the current type of network whether wifi or device data
        //information about currently active network - that is it checked if hardware of these networks (wifi ,data,bluetooth etc is working and not
        // broken)
        val connectivityManager= context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //strikethrough means these classes or functions are deprecated
        //it return current network
        val activeNetwork:NetworkInfo?=connectivityManager.activeNetworkInfo
        if(activeNetwork?.isConnected!=null){
            return activeNetwork.isConnected
        }else{
            return false
        }
    }

}
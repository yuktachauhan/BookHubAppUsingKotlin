package com.yuktachauhan.bookhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DashBoardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //false - because we don;t want to attach this fragment permanently to the activity,we will change
        val view = inflater.inflate(R.layout.fragment_dash_board, container, false)
        return view
    }

}
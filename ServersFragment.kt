package com.servermanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ServersFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_servers, container, false)
        
        recyclerView = view.findViewById(R.id.servers_recycler)
        fab = view.findViewById(R.id.fab_add_server)
        
        recyclerView.layoutManager = LinearLayoutManager(context)
        
        fab.setOnClickListener {
            // Add server dialog
        }
        
        return view
    }
}

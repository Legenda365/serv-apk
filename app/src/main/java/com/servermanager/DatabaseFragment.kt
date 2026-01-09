package com.servermanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import java.sql.DriverManager

class DatabaseFragment : Fragment() {
    private lateinit var hostInput: EditText
    private lateinit var dbNameInput: EditText
    private lateinit var queryInput: EditText
    private lateinit var resultText: TextView
    private lateinit var executeButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_database, container, false)
        
        hostInput = view.findViewById(R.id.db_host)
        dbNameInput = view.findViewById(R.id.db_name)
        queryInput = view.findViewById(R.id.query_input)
        resultText = view.findViewById(R.id.query_result)
        executeButton = view.findViewById(R.id.execute_button)
        
        executeButton.setOnClickListener {
            executeQuery()
        }
        
        return view
    }

    private fun executeQuery() {
        val host = hostInput.text.toString()
        val dbName = dbNameInput.text.toString()
        val query = queryInput.text.toString()
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = "jdbc:postgresql://$host:5432/$dbName"
                val connection = DriverManager.getConnection(url, "<username>", "<password>")
                val statement = connection.createStatement()
                val result = statement.executeQuery(query)
                
                val output = StringBuilder()
                while (result.next()) {
                    val columnCount = result.metaData.columnCount
                    for (i in 1..columnCount) {
                        output.append("${result.metaData.getColumnName(i)}: ${result.getString(i)} ")
                    }
                    output.append("\n")
                }
                
                withContext(Dispatchers.Main) {
                    resultText.text = output.toString()
                }
                
                connection.close()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    resultText.text = "Error: ${e.message}"
                }
            }
        }
    }
}

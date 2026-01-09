package com.servermanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import kotlinx.coroutines.*

class TerminalFragment : Fragment() {
    private lateinit var terminalOutput: TextView
    private lateinit var commandInput: EditText
    private var sshSession: Session? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_terminal, container, false)
        
        terminalOutput = view.findViewById(R.id.terminal_output)
        commandInput = view.findViewById(R.id.command_input)
        
        commandInput.setOnEditorActionListener { _, _, _ ->
            executeCommand(commandInput.text.toString())
            commandInput.text.clear()
            true
        }
        
        return view
    }

    private fun executeCommand(command: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jsch = JSch()
                val session = jsch.getSession("<username>", "<server_ip>", 22)
                session.setPassword("<password>")
                session.setConfig("StrictHostKeyChecking", "no")
                session.connect()

                val channel = session.openChannel("exec")
                (channel as com.jcraft.jsch.ChannelExec).setCommand(command)
                
                val inputStream = channel.inputStream
                channel.connect()
                
                val result = inputStream.bufferedReader().readText()
                
                withContext(Dispatchers.Main) {
                    terminalOutput.append("$ $command\n$result\n")
                }
                
                channel.disconnect()
                session.disconnect()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    terminalOutput.append("Error: ${e.message}\n")
                }
            }
        }
    }
}

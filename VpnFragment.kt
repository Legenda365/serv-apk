package com.servermanager

import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment

class VpnFragment : Fragment() {
    private lateinit var vpnSwitch: Switch
    private lateinit var statusText: TextView
    private lateinit var connectButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vpn, container, false)
        
        vpnSwitch = view.findViewById(R.id.vpn_switch)
        statusText = view.findViewById(R.id.vpn_status)
        connectButton = view.findViewById(R.id.connect_button)
        
        connectButton.setOnClickListener {
            val intent = VpnService.prepare(context)
            if (intent != null) {
                startActivityForResult(intent, VPN_REQUEST_CODE)
            } else {
                startVpn()
            }
        }
        
        vpnSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                startVpn()
            } else {
                stopVpn()
            }
        }
        
        return view
    }

    private fun startVpn() {
        val intent = Intent(context, com.servermanager.VpnService::class.java)
        context?.startService(intent)
        statusText.text = "VPN Connected"
    }

    private fun stopVpn() {
        val intent = Intent(context, com.servermanager.VpnService::class.java)
        context?.stopService(intent)
        statusText.text = "VPN Disconnected"
    }

    companion object {
        const val VPN_REQUEST_CODE = 100
    }
}

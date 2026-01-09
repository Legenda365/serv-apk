package com.servermanager

import android.net.VpnService
import android.content.Intent
import android.os.ParcelFileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream

class VpnService : VpnService() {
    private var vpnInterface: ParcelFileDescriptor? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return if (vpnInterface == null) {
            startVpn()
            START_STICKY
        } else {
            START_NOT_STICKY
        }
    }

    private fun startVpn() {
        val builder = Builder()
        builder.setMtu(1500)
        builder.addAddress("10.0.0.2", 32)
        builder.addRoute("0.0.0.0", 0)
        builder.addDnsServer("8.8.8.8")
        builder.setSession("ServerManager VPN")

        vpnInterface = builder.establish()
        
        Thread {
            try {
                val input = FileInputStream(vpnInterface!!.fileDescriptor)
                val output = FileOutputStream(vpnInterface!!.fileDescriptor)
                
                val buffer = ByteArray(32767)
                while (true) {
                    val length = input.read(buffer)
                    if (length > 0) {
                        // Process VPN packets
                        output.write(buffer, 0, length)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    override fun onDestroy() {
        vpnInterface?.close()
        super.onDestroy()
    }
}

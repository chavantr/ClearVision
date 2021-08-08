package com.mywings.clearvision

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val permissions =
        arrayOf(Manifest.permission.CAMERA)
    private val EXTERNAL_REQUEST = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, permissions,
                EXTERNAL_REQUEST
            )
        }

        btnScanQR.setOnClickListener {
            val intent = Intent(this@MainActivity, ScanQRCodeActivity::class.java)
            intent.putExtra("flag", true)
            startActivity(intent)
        }

        btnLocateMe.setOnClickListener {

        }

        btnTakeMedicine.setOnClickListener {
            val intent = Intent(this@MainActivity, ScanQRCodeActivity::class.java)
            intent.putExtra("flag", false)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            lblWarning.visibility = View.GONE
            btnLocateMe.visibility = View.VISIBLE
            btnScanQR.visibility = View.VISIBLE
            btnTakeMedicine.visibility = View.VISIBLE
        } else {
            lblWarning.visibility = View.VISIBLE
            btnLocateMe.visibility = View.GONE
            btnScanQR.visibility = View.GONE
            btnTakeMedicine.visibility = View.GONE
        }
    }
}
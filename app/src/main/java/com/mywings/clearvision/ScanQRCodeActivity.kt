package com.mywings.clearvision

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback

class ScanQRCodeActivity : AppCompatActivity() {

    private var mCodeScanner: CodeScanner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr_code_scanner_activity)
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        mCodeScanner = CodeScanner(this, scannerView)
        mCodeScanner?.decodeCallback = DecodeCallback { result ->
            runOnUiThread {
                
            }
        }
    }

    private fun stopPreview() {
        mCodeScanner?.releaseResources()
    }

    private fun startPreview() {
        mCodeScanner?.startPreview()
    }

    override fun onResume() {
        super.onResume()
        startPreview()
    }

    override fun onPause() {
        super.onPause()
        stopPreview()
    }
}
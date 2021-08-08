package com.mywings.clearvision

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
                val intent = Intent(this@ScanQRCodeActivity, SuccessResultActivity::class.java)
                intent.putExtra("extra", result.toString())
                intent.putExtra("flag", getIntent().getBooleanExtra("flag", false))
                startActivity(intent)
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
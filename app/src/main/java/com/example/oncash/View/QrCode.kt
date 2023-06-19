package com.example.oncash.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.oncash.R
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder


class QrCode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)

        //Qrcode is generated using userid, restaurant_id , offer_id , and current time

        val resataurant_id :String =  intent.getStringExtra("")!!
        val user_id : String =  intent.getStringExtra("")!!
        val offer_id : String =  intent.getStringExtra("")!!
        val time_stamp : String =  intent.getStringExtra("")!!

        val qrcode :String = "$resataurant_id$user_id$offer_id$time_stamp"
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap(qrcode, BarcodeFormat.QR_CODE, 400, 400)
            val imageViewQrCode = findViewById<ImageView>(R.id.Qrcode_ImageView)
            imageViewQrCode.setImageBitmap(bitmap)
        } catch (_: Exception) {
        }

        val close_button = findViewById<Button>(R.id.qrcodeClose_button)
        close_button.setOnClickListener{
            startActivity(Intent(this , Home::class.java))
        }
    }
}
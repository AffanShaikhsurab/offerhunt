package com.example.oncash.View


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.oncash.DataType.Records
import com.example.oncash.R
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class OfferInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_info)


    }
    suspend fun getOfferData(): List<Records> = withContext(Dispatchers.IO) {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val url = "https://teyqrrnuvucqdvqiadgl.supabase.co/rest/v1/User"
        lateinit var response: HttpResponse
        var offerData: List<Records> = emptyList()

        try {
            response = client.get {
                url(url)
                header("apikey", apiKey)
                header("Authorization", "Bearer $apiKey")
                contentType(ContentType.Application.Json)
            }

            if (response.status.isSuccess()) {
                offerData = response.receive()
            }
        } catch (e: Exception) {
            Log.i("Supabase", e.toString())
        }

        return@withContext offerData
    }

}
package com.djoudinis.barcalculator.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OsmPlacesService {

    private val client = OkHttpClient()
    private val gson = Gson()

    companion object {
        private const val OVERPASS_API_URL = "https://overpass-api.de/api/interpreter"
        private const val RADIUS_METERS = 1000 // Search radius in meters
        private const val TAG = "OsmPlacesService"
    }

    // Data classes to parse Overpass API response
    data class OverpassResponse(
        @SerializedName("elements") val elements: List<OsmElement>
    )

    data class OsmElement(
        @SerializedName("id") val id: Long,
        @SerializedName("lat") val lat: Double,
        @SerializedName("lon") val lon: Double,
        @SerializedName("tags") val tags: Map<String, String>
    ) {
        val name: String? get() = tags["name"]
        val amenity: String? get() = tags["amenity"]
    }

    suspend fun findNearbyBars(latitude: Double, longitude: Double): List<Pair<String, String>> = withContext(Dispatchers.IO) {
        val query = """
            [out:json];
            (node["amenity"~"^(bar|pub|biergarten)$"](
                ${latitude - 0.01},${longitude - 0.01},${latitude + 0.01},${longitude + 0.01}
            );
            way["amenity"~"^(bar|pub|biergarten)$"](
                ${latitude - 0.01},${longitude - 0.01},${latitude + 0.01},${longitude + 0.01}
            );
            rel["amenity"~"^(bar|pub|biergarten)$"](
                ${latitude - 0.01},${longitude - 0.01},${latitude + 0.01},${longitude + 0.01}
            );
            ); 
            out center; 
        """.trimIndent()

        val request = Request.Builder()
            .url(OVERPASS_API_URL)
            .post(query.toRequestBody("application/x-www-form-urlencoded".toMediaTypeOrNull()))
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    responseBody?.let {
                        val overpassResponse = gson.fromJson(it, OverpassResponse::class.java)
                        return@withContext overpassResponse.elements
                            .filter { element -> element.name != null }
                            .map { element -> Pair(element.name!!, element.name!!) } // Using name as both ID and display name for simplicity
                    }
                } else {
                    Log.e(TAG, "Overpass API request failed: ${response.code} ${response.message}")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error calling Overpass API: ${e.message}", e)
        }
        return@withContext emptyList()
    }
}

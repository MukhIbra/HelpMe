package com.example.joshqinshop.networking


import com.example.helpme.data.LocationDetails
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {

    @POST("/location")
    fun postData(@Body locationDetails: LocationDetails): Call<LocationDetails>

    @GET("/search")
    fun getNearbyUsers(@Query("phoneNumber") phoneNumber: String): Call<List<String>>

}
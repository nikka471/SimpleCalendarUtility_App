package com.poliarc.simplecalendarutilityapp.data.remote

import com.poliarc.simplecalendarutilityapp.data.model.Event
import com.poliarc.simplecalendarutilityapp.data.model.EventResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("events.json")
    fun getEvents(): Call<EventResponse>
}

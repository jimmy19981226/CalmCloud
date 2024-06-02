package com.example.calmcloud.api

import com.example.calmcloud.entity.Mood
import com.example.calmcloud.entity.Sleep
import com.example.calmcloud.entity.StressLevel
import com.example.calmcloud.entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    fun loginUser(@Body user: User): Call<User>

    @POST("register")
    fun registerUser(@Body user: User): Call<User>

    @GET("moods")
    fun getMoods(): Call<List<Mood>>

    @POST("moods")
    fun createMood(@Body mood: Mood): Call<Mood>

    @GET("stress-levels")
    fun getStressLevels(): Call<List<StressLevel>>

    @POST("stress-levels")
    fun createStressLevel(@Body stressLevel: StressLevel): Call<StressLevel>

    @GET("sleep")
    fun getSleepData(): Call<List<Sleep>>

    @POST("sleep")
    fun createSleepData(@Body sleep: Sleep): Call<Sleep>
}

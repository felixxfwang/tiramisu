package org.tiramisu.network.service

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.1.105:5000/") // 设置网络请求的公共Url地址
    .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
    .build()

interface VideoService {

    @GET("videos")
    fun getVideos(): Call<VideoQueryResult>
}
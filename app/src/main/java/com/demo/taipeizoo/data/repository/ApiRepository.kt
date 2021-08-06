package com.demo.taipeizoo.data.repository

import com.demo.taipeizoo.data.api.ApiService
import com.demo.taipeizoo.data.api.NetworkManager

/**
 * ApiRepository
 *
 * Created by TeyaHong on 2021/8/4
 */
class ApiRepository {
    private val apiService: ApiService = NetworkManager.create(ApiService::class.java)

    suspend fun getAreas() = apiService.getAreas()

    suspend fun getPlants(q: String, offset: Int) = apiService.getPlants(q = q, offset = offset)
}
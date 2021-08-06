package com.demo.taipeizoo.data.api

import com.demo.taipeizoo.data.model.ApiResponse
import com.demo.taipeizoo.data.model.Area
import com.demo.taipeizoo.data.model.Plant
import com.demo.taipeizoo.utils.Constants.LIST_NO
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * ApiService
 *
 * Created by TeyaHong on 2021/8/4
 */
interface ApiService {
    /**
     * 館區列表
     */
    @GET("5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
    suspend fun getAreas(
        @Query("scope") scope: String = "resourceAquire"
    ): ApiResponse<Area>

    /**
     * 植物資料
     *
     * @param q 關鍵字查詢
     * @param limit 筆數上限
     * @param offset 位移筆數
     */
    @GET("f18de02f-b6c9-47c0-8cda-50efad621c14")
    suspend fun getPlants(
        @Query("scope") scope: String = "resourceAquire",
        @Query("q") q: String,
        @Query("limit") limit: Int = LIST_NO,
        @Query("offset") offset: Int
    ): ApiResponse<Plant>
}
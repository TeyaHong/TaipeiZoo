package com.demo.taipeizoo.data.model

data class ApiResponse<T>(
    val result: Result<T>
)

data class Result<T>(
    val limit: Int,
    val offset: Int,
    val count: Int,
    val results: List<T>
)

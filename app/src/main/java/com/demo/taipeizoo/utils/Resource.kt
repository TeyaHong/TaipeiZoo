package com.demo.taipeizoo.utils

/**
 * Resource 將網路請求狀態傳給UI層
 *
 * Created by TeyaHong on 2021/8/4
 */
data class Resource<out T>(val status: Status, val data: T?, val error: String?) {
    companion object {
        fun <T> success(data: T?) =
            Resource(status = Status.SUCCESS, data = data, error = null)

        fun <T> error(data: T? = null, error: String?) =
            Resource(status = Status.ERROR, data = data, error = error)

        fun <T> loading(data: T? = null) =
            Resource(status = Status.LOADING, data = data, error = null)
    }
}

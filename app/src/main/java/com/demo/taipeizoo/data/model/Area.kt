package com.demo.taipeizoo.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Area
 *
 * Created by TeyaHong on 2021/8/4
 */
data class Area(
    // 圖片URL
    @SerializedName("E_Pic_URL")
    val pic: String,
    // 館名
    @SerializedName("E_Name")
    val name: String,
    // 館區導覽
    @SerializedName("E_Info")
    val info: String,
    // 備註
    @SerializedName("E_Memo")
    val memo: String,
    // 區域
    @SerializedName("E_Category")
    val category: String,
    // 相關連結
    @SerializedName("E_URL")
    val url: String
) : Serializable
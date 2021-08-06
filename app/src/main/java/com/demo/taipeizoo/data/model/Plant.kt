package com.demo.taipeizoo.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Plant
 *
 * Created by TeyaHong on 2021/8/4
 */
data class Plant(
    // 照片url
    @SerializedName("F_Pic01_URL")
    val pic: String,
    // 中文名
    @SerializedName("\uFEFFF_Name_Ch")
    val nameCh: String,
    // 英文名
    @SerializedName("F_Name_En")
    val nameEn: String,
    // 別名
    @SerializedName("F_AlsoKnown")
    val alsoKnown: String,
    // 簡介
    @SerializedName("F_Brief")
    val brief: String,
    // 辨認方式
    @SerializedName("F_Feature")
    val feature: String,
    // 功能性
    @SerializedName("F_Function＆Application")
    val function: String,
    // 資料更新日期
    @SerializedName("F_Update")
    val update: String
) : Serializable

package com.demo.taipeizoo.utils

import android.content.res.Resources

/**
 * Utils
 *
 * Created by TeyaHong on 2021/8/4
 */

//// -----------------------------------------------------------------------------------------------
//// Display scale convert Util
//// -----------------------------------------------------------------------------------------------
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
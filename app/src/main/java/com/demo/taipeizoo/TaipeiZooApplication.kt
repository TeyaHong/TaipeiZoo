package com.demo.taipeizoo

import android.app.Application

/**
 * TaipeiZooApplication
 *
 * Created by TeyaHong on 2021/8/4
 */
class TaipeiZooApplication : Application() {
    companion object {
        lateinit var instance: TaipeiZooApplication
            private set
    }

    init {
        instance = this
    }
}
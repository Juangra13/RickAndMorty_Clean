package com.jgcb.rickandmorty

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

@HiltAndroidApp
class ApplicationMain : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
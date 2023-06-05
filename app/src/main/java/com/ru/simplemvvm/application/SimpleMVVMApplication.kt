package com.ru.simplemvvm.application

import android.app.Application

class SimpleMVVMApplication : Application() {
    override fun onCreate() {
        Initializer.initDependencies()
        super.onCreate()
    }
}
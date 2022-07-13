package com.nextory.testapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TestApplication : Application() {

	override fun onCreate() {
		super.onCreate()
		Timber.plant(Timber.DebugTree())
	}
}
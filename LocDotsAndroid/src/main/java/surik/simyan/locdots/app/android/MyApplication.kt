package surik.simyan.locdots.app.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import surik.simyan.locdots.app.android.di.androidModule
import surik.simyan.locdots.app.shared.di.sharedModule

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MyApplication)
            // Load modules
            modules(androidModule + sharedModule)
        }
    }
}
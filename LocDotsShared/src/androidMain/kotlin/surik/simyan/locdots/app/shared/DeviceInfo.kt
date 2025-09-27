package surik.simyan.locdots.app.shared

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import org.koin.mp.KoinPlatform.getKoin

@SuppressLint("HardwareIds")
actual fun getDeviceId(): String {
    val context: Context = getKoin().get()
    return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}
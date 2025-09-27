package surik.simyan.locdots.app.shared

import platform.UIKit.UIDevice

actual fun getDeviceId(): String = UIDevice.currentDevice.identifierForVendor?.UUIDString.orEmpty()

package surik.simyan.locdots.app

import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import surik.simyan.locdots.app.shared.data.Dot
import surik.simyan.locdots.app.shared.data.DotSort
import surik.simyan.locdots.app.shared.di.sharedModule
import surik.simyan.locdots.app.shared.network.DotsApi

class DotsApiHelper : KoinComponent {
    private val dotsApi: DotsApi by inject()
    fun getAllDots(lat: Double, lng: Double, sortingType: DotSort): Flow<List<Dot>> =
        dotsApi.getAllDots(lat, lng, sortingType)
}

fun initKoin() {
    startKoin {
        modules(sharedModule)
    }
}
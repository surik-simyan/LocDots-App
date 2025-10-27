package surik.simyan.locdots.app.shared.di

import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.mobile
import org.koin.dsl.module
import surik.simyan.locdots.app.shared.domain.usecases.CreateDotUseCase
import surik.simyan.locdots.app.shared.domain.usecases.CreateDotUseCaseImpl
import surik.simyan.locdots.app.shared.domain.usecases.GetAllDotsUseCase
import surik.simyan.locdots.app.shared.domain.usecases.GetAllDotsUseCaseImpl
import surik.simyan.locdots.app.shared.network.DotsApiService
import surik.simyan.locdots.app.shared.network.DotsApiServiceImpl
import surik.simyan.locdots.app.shared.network.client

val sharedModule = module {
    single { client }
    single<DotsApiService> { DotsApiServiceImpl(get()) }
    single<Geolocator> { Geolocator.mobile() }
    factory<GetAllDotsUseCase> { GetAllDotsUseCaseImpl(get(), get()) }
    factory<CreateDotUseCase> { CreateDotUseCaseImpl(get(), get()) }
}

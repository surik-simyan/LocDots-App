package surik.simyan.locdots.app.shared.di

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
    factory<GetAllDotsUseCase> { GetAllDotsUseCaseImpl(get()) }
    factory<CreateDotUseCase> { CreateDotUseCaseImpl(get()) }
}

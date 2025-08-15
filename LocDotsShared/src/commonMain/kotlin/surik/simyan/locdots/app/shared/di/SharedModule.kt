package surik.simyan.locdots.app.shared.di

import org.koin.dsl.module
import surik.simyan.locdots.app.shared.network.DotsApi

val sharedModule = module {
    single<DotsApi> { DotsApi() }
}

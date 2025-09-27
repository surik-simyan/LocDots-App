package surik.simyan.locdots.app

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import surik.simyan.locdots.app.shared.di.sharedModule
import surik.simyan.locdots.app.shared.domain.usecases.CreateDotUseCase
import surik.simyan.locdots.app.shared.domain.usecases.GetAllDotsUseCase

@Suppress("unused")
object UseCaseProvider : KoinComponent {
    val getAllDotsUseCase: GetAllDotsUseCase by inject()
    val createDotUseCase: CreateDotUseCase by inject()
}

@Suppress("unused")
fun initKoin() {
    startKoin {
        modules(sharedModule)
    }
}
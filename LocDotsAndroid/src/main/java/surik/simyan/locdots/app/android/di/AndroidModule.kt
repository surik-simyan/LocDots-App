package surik.simyan.locdots.app.android.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import surik.simyan.locdots.app.android.ui.screens.HomeScreenViewModel
import surik.simyan.locdots.app.android.ui.screens.MessageScreenViewModel

val androidModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::MessageScreenViewModel)
}
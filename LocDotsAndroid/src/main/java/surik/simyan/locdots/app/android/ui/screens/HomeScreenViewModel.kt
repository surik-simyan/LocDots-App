package surik.simyan.locdots.app.android.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import surik.simyan.locdots.app.shared.base.onFailure
import surik.simyan.locdots.app.shared.base.onSuccess
import surik.simyan.locdots.app.shared.data.DotSort
import surik.simyan.locdots.app.shared.domain.model.Dot
import surik.simyan.locdots.app.shared.domain.usecases.GetAllDotsUseCase

class HomeScreenViewModel(
    private val getAllDots: GetAllDotsUseCase,
) : ViewModel() {

    private val _homeScreenState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState.Idle)
    val homeScreenState = _homeScreenState.asStateFlow()

    val sortingType = MutableStateFlow(DotSort.PostDate)

    init {
        getItems()
    }

    sealed class HomeScreenState {
        data object Idle : HomeScreenState()
        data object Loading : HomeScreenState()
        data class Error(val error: String) : HomeScreenState()
        data class Success(val items: List<Dot>) : HomeScreenState()
    }

    fun getItems() {
        viewModelScope.launch {
            _homeScreenState.update { HomeScreenState.Loading }
            getAllDots.invoke(sortingType.value)
                .onSuccess { dots ->
                    _homeScreenState.update { HomeScreenState.Success(dots) }
                }
                .onFailure { error ->
                    _homeScreenState.update { HomeScreenState.Error(error.message) }
                }
        }
    }

    fun resetState() = _homeScreenState.update { HomeScreenState.Idle }
}

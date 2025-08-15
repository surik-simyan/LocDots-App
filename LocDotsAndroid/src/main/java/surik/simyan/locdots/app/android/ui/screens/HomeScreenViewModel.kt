package surik.simyan.locdots.app.android.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import surik.simyan.locdots.app.shared.data.Dot
import surik.simyan.locdots.app.shared.data.DotSort
import surik.simyan.locdots.app.shared.network.DotsApi

class HomeScreenViewModel(
    private val dotsApi: DotsApi
) : ViewModel() {

    private val _homeScreenState: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState.Idle)
    val homeScreenState = _homeScreenState.asStateFlow()

    //
//    private val geolocator: Geolocator = Geolocator.mobile()
//
    val sortType = MutableStateFlow(DotSort.PostDate)

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
            delay(5000)
            dotsApi.getAllDots(40.741895, -73.989308, sortType.value).collect { dots ->
                _homeScreenState.update { HomeScreenState.Success(dots) }
            }
//            when (val location = geolocator.current(Priority.HighAccuracy)) {
//                is GeolocatorResult.Success -> {
//                    try {
//                        _dots.update {
//                            HomeScreenState.Success(
//                                dotsApi.getAllDots(
//                                    location.data.coordinates.latitude,
//                                    location.data.coordinates.longitude,
//                                    sortType.value
//                                )
//                            )
//                        }
//                    } catch (e: Exception) {
//                        Logger.e(e.message.toString(), e)
//                        _dots.update { HomeScreenState.Error(e.toString()) }
//                    }
//                }
//
//                is GeolocatorResult.PermissionError -> {
//                    Logger.e("PermissionError")
//                    _dots.update { HomeScreenState.Error("Please grant location permission") }
//                }
//
//                else -> {
//                    Logger.e(location.toString())
//                    _dots.update { HomeScreenState.Error("Something went wrong") }
//                }
//            }
        }
    }

    fun resetState() = _homeScreenState.update { HomeScreenState.Idle }
}
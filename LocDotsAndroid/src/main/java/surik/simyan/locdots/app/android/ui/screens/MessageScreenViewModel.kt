package surik.simyan.locdots.app.android.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import surik.simyan.locdots.app.shared.base.onFailure
import surik.simyan.locdots.app.shared.base.onSuccess
import surik.simyan.locdots.app.shared.domain.usecases.CreateDotUseCase

class MessageScreenViewModel(
    private val createDot: CreateDotUseCase,
) : ViewModel() {

    private val _uploadState: MutableStateFlow<MessageScreenState> =
        MutableStateFlow(MessageScreenState.Idle)
    val uploadState = _uploadState.asStateFlow()

//    private val geolocator: Geolocator = Geolocator.mobile()

    sealed class MessageScreenState {
        data object Idle : MessageScreenState()
        data object Loading : MessageScreenState()
        data class Error(val error: String) : MessageScreenState()
        data object Success : MessageScreenState()
    }

    fun onSendClick(message: String) {
        viewModelScope.launch {
            _uploadState.update { MessageScreenState.Loading }
            createDot.invoke(message)
                .onSuccess {
                    _uploadState.update { MessageScreenState.Success }
                }
                .onFailure { error ->
                    _uploadState.update { MessageScreenState.Error(error.message) }
                }
        }
//        viewModelScope.launch {
//            _uploadState.update { MessageScreenState.Loading }
//            when (val location = geolocator.current(Priority.HighAccuracy)) {
//                is GeolocatorResult.Success -> {
//                    try {
//                        dotsApi.createNewDot(
//                            Dot(
//                                timestamp = Clock.System.now().epochSeconds,
//                                coordinates = Pair(
//                                    location.data.coordinates.longitude,
//                                    location.data.coordinates.latitude
//                                ),
//                                message = message
//                            )
//                        )
//                        _uploadState.update {
//                            MessageScreenState.Success
//                        }
//                    } catch (e: Exception) {
//                        Logger.e(e.message.toString(), e)
//                        _uploadState.update { MessageScreenState.Error(e.toString()) }
//                    }
//                }
//
//                is GeolocatorResult.PermissionError -> {
//                    Logger.e("PermissionError")
//                    _uploadState.update { MessageScreenState.Error("Please grant location permission") }
//                }
//
//                else -> {
//                    Logger.e(location.toString())
//                    _uploadState.update { MessageScreenState.Error("Something went wrong") }
//                }
//            }
//        }
    }

    fun resetState() = _uploadState.update { MessageScreenState.Idle }
}

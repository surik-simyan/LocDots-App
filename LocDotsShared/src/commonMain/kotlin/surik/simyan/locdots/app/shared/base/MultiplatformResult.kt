package surik.simyan.locdots.app.shared.base

import kotlinx.serialization.Serializable

@Serializable
sealed class MultiplatformResult<out T> {
    @Serializable
    data class Success<T>(val data: T) : MultiplatformResult<T>()

    @Serializable
    data class Error(val message: String, val cause: String? = null) :
        MultiplatformResult<Nothing>()
}

inline fun <T> MultiplatformResult<T>.onSuccess(action: (T) -> Unit): MultiplatformResult<T> {
    if (this is MultiplatformResult.Success) action(data)
    return this
}

inline fun <T> MultiplatformResult<T>.onFailure(action: (MultiplatformResult.Error) -> Unit): MultiplatformResult<T> {
    if (this is MultiplatformResult.Error) action(this)
    return this
}
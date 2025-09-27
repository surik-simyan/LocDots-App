package surik.simyan.locdots.app.shared.network

class ApiException(message: String, cause: Throwable? = null) : Exception(message, cause)

class ApiServerException(
    val code: String,
    message: String,
    val traceId: String?
) : Exception(message)

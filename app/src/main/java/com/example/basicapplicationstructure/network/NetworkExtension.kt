package com.example.basicapplicationstructure.network

suspend fun Int.invokeOnStatus(onSuccess: (suspend () -> Unit)?, onError: (suspend () -> Unit)?) {
    when (this) {
        200 -> onSuccess?.invoke()
        else -> onError?.invoke()
    }
}
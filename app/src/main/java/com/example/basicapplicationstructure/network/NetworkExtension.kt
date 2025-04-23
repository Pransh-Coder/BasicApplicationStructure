package com.example.basicapplicationstructure.network

//todo needs to fix it (better way for this)
suspend fun Int.invokeOnStatus(onSuccess: (suspend () -> Unit)?, onError: (suspend () -> Unit)?) {
    when (this) {
        200 -> onSuccess?.invoke()
        else -> onError?.invoke()
    }
}
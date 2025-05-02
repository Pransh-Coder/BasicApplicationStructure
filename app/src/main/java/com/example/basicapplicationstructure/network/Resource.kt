package com.example.basicapplicationstructure.network
//todo resource class
//Use <T> for clean, safe, and scalable code.
//Use Any only if you're dealing with truly unknown or mixed types — which is rare in well-structured apps.

/* <T>
✅ The type is preserved.
✅ The compiler knows it's List<Movie> — no casting needed.
✅ You get full IntelliSense/autocomplete for fields, safe handling.*/

sealed class Resource<T>(open val data:T ? = null, open val errorMessage: String? = null){

    //class Loading<T>() : NetworkResponse<T>()

    data class Success<T>(override val data: T) : Resource<T>(data)

    data class Error<T>(override val errorMessage: String) : Resource<T>(errorMessage = errorMessage)

    class NoInternetException<T>: Resource<T>()
}

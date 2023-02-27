package com.mutakinsinau.pokemontcg.data.network.util

sealed class ApiResult<T>(val data:T? = null, val error: String? = null) {
    class Success<T>(data: T) : ApiResult<T>(data)
    class Error(errorMsg: String) : ApiResult<Nothing>(error = errorMsg)
    class Loading<T>(data: T? = null) : ApiResult<T>(data = data)
}

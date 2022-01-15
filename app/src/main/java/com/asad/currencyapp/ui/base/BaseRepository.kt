package com.asad.currencyapp.ui.base

import com.asad.currencyapp.data.remote.models.base.State
import retrofit2.Response
import javax.inject.Inject

open class BaseRepository @Inject constructor() {
    suspend fun <T : Any> makeApiCall(
        call: suspend () -> Response<T>,
    ): State<T> {
        val response = call.invoke()
        return if (response.isSuccessful)
            State.Success(response.body()!!)
        else {
            State.Error(State.ResponseError.SomethingWentWrong())
        }
    }
}
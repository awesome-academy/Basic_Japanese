package com.sun.basic_japanese.data.source.remote

import android.os.AsyncTask
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class GetResponseAsync<T>(
    private val responseHandler: DataResponseHandler<T>,
    private val callback: OnDataLoadedCallback<T>
) : AsyncTask<String, Unit, T?>() {

    private var exception: Exception? = null

    override fun doInBackground(vararg params: String): T? =
        try {
            responseHandler.getResponse(params[0])
        } catch (exception: Exception) {
            this.exception = exception
            null
        }

    override fun onPostExecute(result: T?) {
        result?.let {
            callback.onSuccess(it)
        } ?: exception?.let {
            callback.onFailed(it)
        }
    }
}

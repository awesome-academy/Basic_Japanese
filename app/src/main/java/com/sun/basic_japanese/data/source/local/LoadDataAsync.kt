package com.sun.basic_japanese.data.source.local

import android.os.AsyncTask
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class LoadDataAsync<T>(
    private val callback: OnDataLoadedCallback<T>
) : AsyncTask<T, Unit, T>() {

    private var exception: Exception? = null

    override fun doInBackground(vararg params: T): T? =
        try {
            params.first()
        } catch (e: Exception) {
            exception = e
            null
        }

    override fun onPostExecute(result: T) {
        result?.let {
            callback.onSuccess(it)
        } ?: exception?.let {
            callback.onFailed(it)
        }
    }
}

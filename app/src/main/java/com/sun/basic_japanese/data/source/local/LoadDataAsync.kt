package com.sun.basic_japanese.data.source.local

import android.os.AsyncTask
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class LoadDataAsync<P, T>(
    private val handler: LocalDataHandler<P, T>,
    private val callback: OnDataLoadedCallback<T>
) : AsyncTask<P, Unit, T>() {

    private var exception: Exception? = null

    override fun doInBackground(vararg params: P): T? {
        return try {
            handler.execute(*params) ?: throw Exception()
        } catch (e: Exception) {
            exception = e
            null
        }
    }

    override fun onPostExecute(result: T?) {
        result?.let {
            callback.onSuccess(it)
        } ?: exception?.let {
            callback.onFailed(it)
        }
    }
}

package com.sun.basic_japanese.data.source.local

import com.sun.basic_japanese.data.model.JLPTTest
import com.sun.basic_japanese.data.model.JLPTTestResponse
import com.sun.basic_japanese.data.source.JLPTTestDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class JLPTTestLocalDataSource private constructor(
    private val database: AppDatabase
) : JLPTTestDataSource.Local {

    override fun getJLPTTestLocal(
        category: String,
        callback: OnDataLoadedCallback<JLPTTestResponse>
    ) {
        LoadDataAsync(callback).execute(JLPTTestResponse(database.getJLPTTestLocal(category)))
    }

    override fun updateJLPTTestLocal(
        jlptTests: List<JLPTTest>,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(callback).execute(database.updateJLPTTestLocal(jlptTests))
    }

    companion object {

        @Volatile
        private var instance: JLPTTestLocalDataSource? = null

        fun getInstance(database: AppDatabase) = instance ?: synchronized(this) {
            instance ?: JLPTTestLocalDataSource(database).also { instance = it }
        }
    }
}

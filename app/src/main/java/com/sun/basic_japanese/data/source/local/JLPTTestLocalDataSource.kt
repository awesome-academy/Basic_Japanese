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
        LoadDataAsync(object : LocalDataHandler<String, JLPTTestResponse> {
            override fun execute(vararg params: String): JLPTTestResponse =
                JLPTTestResponse(database.getJLPTTestLocal(params[0]))
        }, callback).execute(category)
    }

    override fun updateJLPTTestLocal(
        jlptTests: List<JLPTTest>,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(object : LocalDataHandler<List<JLPTTest>, Boolean> {
            override fun execute(vararg params: List<JLPTTest>): Boolean =
                database.updateJLPTTestLocal(params[0])
        }, callback).execute(jlptTests)
    }

    companion object {

        @Volatile
        private var instance: JLPTTestLocalDataSource? = null

        fun getInstance(database: AppDatabase) = instance ?: synchronized(this) {
            instance ?: JLPTTestLocalDataSource(database).also { instance = it }
        }
    }
}

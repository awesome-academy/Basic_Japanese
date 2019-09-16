package com.sun.basic_japanese.data.repository

import com.sun.basic_japanese.data.model.JLPTTest
import com.sun.basic_japanese.data.model.JLPTTestResponse
import com.sun.basic_japanese.data.source.JLPTTestDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback
import com.sun.basic_japanese.data.source.local.JLPTTestLocalDataSource
import com.sun.basic_japanese.data.source.remote.JLPTTestRemoteDataSource

class JLPTTestRepository private constructor(
    private val localDataSource: JLPTTestLocalDataSource,
    private val remoteDataSource: JLPTTestRemoteDataSource
) : JLPTTestDataSource.Local,
    JLPTTestDataSource.Remote {

    override fun getJLPTTestLocal(
        category: String,
        callback: OnDataLoadedCallback<JLPTTestResponse>
    ) {
        localDataSource.getJLPTTestLocal(category, callback)
    }

    override fun updateJLPTTestLocal(
        jlptTests: List<JLPTTest>,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        localDataSource.updateJLPTTestLocal(jlptTests, callback)
    }

    override fun getJLPTTestRemote(
        category: String,
        callback: OnDataLoadedCallback<JLPTTestResponse>
    ) {
        remoteDataSource.getJLPTTestRemote(category, callback)
    }


    companion object {

        @Volatile
        private var instance: JLPTTestRepository? = null

        fun getInstance(
            local: JLPTTestLocalDataSource,
            remote: JLPTTestRemoteDataSource
        ) = instance ?: synchronized(this) {
            instance ?: JLPTTestRepository(local, remote).also { instance = it }
        }
    }
}

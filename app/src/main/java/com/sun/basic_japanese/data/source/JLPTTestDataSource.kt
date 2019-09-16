package com.sun.basic_japanese.data.source

import com.sun.basic_japanese.data.model.JLPTTest
import com.sun.basic_japanese.data.model.JLPTTestResponse

interface JLPTTestDataSource {

    interface Local {
        fun getJLPTTestLocal(category: String, callback: OnDataLoadedCallback<JLPTTestResponse>)

        fun updateJLPTTestLocal(
            jlptTests: List<JLPTTest>,
            callback: OnDataLoadedCallback<Boolean>
        )
    }

    interface Remote {
        fun getJLPTTestRemote(category: String, callback: OnDataLoadedCallback<JLPTTestResponse>)
    }
}

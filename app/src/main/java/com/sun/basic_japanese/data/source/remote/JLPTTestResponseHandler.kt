package com.sun.basic_japanese.data.source.remote

import com.sun.basic_japanese.data.model.JLPTTestResponse
import org.json.JSONArray

class JLPTTestResponseHandler : DataResponseHandler<JLPTTestResponse> {
    override fun parseToObject(string: String): JLPTTestResponse {
        return JLPTTestResponse(JSONArray(string))
    }
}

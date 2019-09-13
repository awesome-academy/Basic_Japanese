package com.sun.basic_japanese.data.source.remote

import com.sun.basic_japanese.data.model.KanjiAdvanceResponse
import org.json.JSONArray

class KanjiAdvanceResponseHandler : DataResponseHandler<KanjiAdvanceResponse> {
    override fun parseToObject(string: String): KanjiAdvanceResponse {
        return KanjiAdvanceResponse(JSONArray(string))
    }
}

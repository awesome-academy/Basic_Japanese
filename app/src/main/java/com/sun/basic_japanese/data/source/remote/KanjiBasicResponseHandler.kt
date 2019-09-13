package com.sun.basic_japanese.data.source.remote

import com.sun.basic_japanese.data.model.KanjiBasicResponse
import org.json.JSONArray

class KanjiBasicResponseHandler : DataResponseHandler<KanjiBasicResponse>{
    override fun parseToObject(string: String): KanjiBasicResponse {
        return KanjiBasicResponse(JSONArray(string))
    }
}

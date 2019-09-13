package com.sun.basic_japanese.util

import android.content.Context
import android.util.Base64
import android.widget.Toast
import com.sun.basic_japanese.data.model.DataRequest
import com.sun.basic_japanese.data.model.Example
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

object Extensions {

    fun Context.showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    @Throws(IOException::class)
    fun HttpURLConnection.build(method: String) = apply {
        requestMethod = method
        connect()
    }

    @Throws(IOException::class)
    fun InputStreamReader.getString(): String {
        val reader = BufferedReader(this)
        return StringBuilder().apply {
            do {
                val inputLine = reader.readLine()
                inputLine?.let { append(inputLine) }
            } while (inputLine != null)
            reader.close()
        }.toString()
    }

    @Throws(IOException::class)
    fun String.parseImage(): String = this.let {
        val imageUrl = DataRequest(
            Constants.SCHEME_HTTP,
            Constants.AUTHORITY_MINNA_MAZIL,
            listOf(Constants.PATH_IMAGE, Constants.PATH_IKANJI, "$it.jpg")
        ).toUrl()
        val connection = URL(imageUrl).openConnection()
        val inputStream = connection.getInputStream()
        Base64.encodeToString(inputStream.readBytes(), Base64.DEFAULT)
    }

    fun MutableList<Example>.parseExamples(input: String) = this.apply {
        val examplesString = input.split(Constants.CHARACTER_SPLIT_1)
        for (index in 1 until examplesString.size) {
            val exampleString = examplesString[index].split(Constants.CHARACTER_SPLIT_2)
            add(Example(exampleString[0], exampleString[1], exampleString[2]))
        }
    }
}

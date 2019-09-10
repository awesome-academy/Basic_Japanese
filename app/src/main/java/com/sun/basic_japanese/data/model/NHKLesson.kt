package com.sun.basic_japanese.data.model

import android.database.Cursor

data class NHKLesson(
    val id: Int,
    val title: String,
    val detail: String,
    val image: String,
    val audio: String,
    val grammar: String,
    val dialogues: String
){
    constructor(cursor: Cursor) : this(
        cursor.getInt(cursor.getColumnIndex(DATABASE_TABLE_NHK_LESSON_COLUMN_ID)),
        cursor.getString(cursor.getColumnIndex(DATABASE_TABLE_NHK_LESSON_COLUMN_TITLE)),
        cursor.getString(cursor.getColumnIndex(DATABASE_TABLE_NHK_LESSON_COLUMN_DETAIL)),
        cursor.getString(cursor.getColumnIndex(DATABASE_TABLE_NHK_LESSON_COLUMN_IMAGE)),
        cursor.getString(cursor.getColumnIndex(DATABASE_TABLE_NHK_LESSON_COLUMN_AUDIO)),
        cursor.getString(cursor.getColumnIndex(DATABASE_TABLE_NHK_LESSON_COLUMN_GRAMMAR)),
        cursor.getString(cursor.getColumnIndex(DATABASE_TABLE_NHK_LESSON_COLUMN_DIALOGUE))
    )

    companion object {
        const val DATABASE_TABLE_NHK_LESSON_COLUMN_ID = "id"
        const val DATABASE_TABLE_NHK_LESSON_COLUMN_TITLE = "title"
        const val DATABASE_TABLE_NHK_LESSON_COLUMN_DETAIL = "detail"
        const val DATABASE_TABLE_NHK_LESSON_COLUMN_IMAGE = "image"
        const val DATABASE_TABLE_NHK_LESSON_COLUMN_AUDIO = "audio"
        const val DATABASE_TABLE_NHK_LESSON_COLUMN_GRAMMAR = "grammar"
        const val DATABASE_TABLE_NHK_LESSON_COLUMN_DIALOGUE = "dialogue"
    }
}

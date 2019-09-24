package com.sun.basic_japanese.data.source.local

import com.sun.basic_japanese.data.model.*
import com.sun.basic_japanese.data.source.NHKLessonDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback
import com.sun.basic_japanese.util.Constants

class NHKLessonLocalDataSource(private val appDatabase: AppDatabase) : NHKLessonDataSource.Local {

    override fun getAllNHKLessonsThumbnails(callback: OnDataLoadedCallback<NHKLessonsThumbnailsResponse>) {
        LoadDataAsync(object : LocalDataHandler<String, NHKLessonsThumbnailsResponse> {
            override fun execute(vararg params: String): NHKLessonsThumbnailsResponse =
                NHKLessonsThumbnailsResponse(appDatabase.getNHKLessonsThumbnails())
        }, callback).execute(Constants.EMPTY_STRING)
    }

    override fun getAllNHKLessons(callback: OnDataLoadedCallback<NHKLessonsResponse>) {
        LoadDataAsync(object : LocalDataHandler<String, NHKLessonsResponse> {
            override fun execute(vararg params: String): NHKLessonsResponse =
                NHKLessonsResponse(appDatabase.getNHKLessons())
        }, callback).execute(Constants.EMPTY_STRING)
    }

    companion object {
        @Volatile
        private var instance: NHKLessonLocalDataSource? = null

        fun getInstance(database: AppDatabase) = instance ?: synchronized(this) {
            instance ?: NHKLessonLocalDataSource(database).also { instance = it }
        }
    }
}

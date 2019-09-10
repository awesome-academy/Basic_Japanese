package com.sun.basic_japanese.data.source.local

import com.sun.basic_japanese.data.model.NHKLessonsResponse
import com.sun.basic_japanese.data.source.NHKLessonDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class NHKLessonLocalDataSource(private val appDatabase: AppDatabase) : NHKLessonDataSource.Local {
    override fun getAllNHKLessons(callback: OnDataLoadedCallback<NHKLessonsResponse>) {
        LoadDataAsync(callback).execute(NHKLessonsResponse(appDatabase.getNHKLessons()))
    }

    companion object {
        @Volatile
        private var instance: NHKLessonLocalDataSource? = null

        fun getInstance(database: AppDatabase) = instance ?: synchronized(this) {
            instance ?: NHKLessonLocalDataSource(database).also { instance = it }
        }
    }
}

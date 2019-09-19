package com.sun.basic_japanese.data.repository

import com.sun.basic_japanese.data.model.NHKLessonsResponse
import com.sun.basic_japanese.data.model.NHKLessonsThumbnailsResponse
import com.sun.basic_japanese.data.source.NHKLessonDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class NHKLessonRepository(
    private val localDataSource: NHKLessonDataSource.Local
) : NHKLessonDataSource.Local {
    override fun getAllNHKLessonsThumbnails(callback: OnDataLoadedCallback<NHKLessonsThumbnailsResponse>) {
        localDataSource.getAllNHKLessonsThumbnails(callback)
    }

    override fun getAllNHKLessons(callback: OnDataLoadedCallback<NHKLessonsResponse>) {
        localDataSource.getAllNHKLessons(callback)
    }

    companion object {
        @Volatile
        private var instance: NHKLessonRepository? = null

        fun getInstance(local: NHKLessonDataSource.Local) = instance ?: synchronized(this) {
            instance ?: NHKLessonRepository(local).also { instance = it }
        }
    }
}

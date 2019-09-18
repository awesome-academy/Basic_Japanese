package com.sun.basic_japanese.data.source

import com.sun.basic_japanese.data.model.NHKLessonsResponse
import com.sun.basic_japanese.data.model.NHKLessonsThumbnailsResponse

interface NHKLessonDataSource {
    interface Local {
        fun getAllNHKLessons(callback: OnDataLoadedCallback<NHKLessonsResponse>)
        fun getAllNHKLessonsThumbnails(callback: OnDataLoadedCallback<NHKLessonsThumbnailsResponse>)
    }
}

package com.sun.basic_japanese.data.source

import com.sun.basic_japanese.data.model.NHKLessonsResponse

interface NHKLessonDataSource {
    interface Local {
        fun getAllNHKLessons(callback: OnDataLoadedCallback<NHKLessonsResponse>)
    }
}

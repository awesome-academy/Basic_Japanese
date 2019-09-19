package com.sun.basic_japanese.audiolessons

import com.sun.basic_japanese.data.model.NHKLessonsResponse
import com.sun.basic_japanese.data.model.NHKLessonsThumbnailsResponse
import com.sun.basic_japanese.data.repository.NHKLessonRepository
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class AudioLessonsPresenter(
    private val pageView: AudioLessonsContract.View,
    private val nhkLessonRepository: NHKLessonRepository
) : AudioLessonsContract.Presenter {
    override fun getLessonsData() {
        nhkLessonRepository.getAllNHKLessons(object : OnDataLoadedCallback<NHKLessonsResponse> {
            override fun onSuccess(data: NHKLessonsResponse) {
                pageView.setLessonsData(data.nhkLessons)
            }

            override fun onFailed(exception: Exception) {
                TODO()
            }
        })
        nhkLessonRepository.getAllNHKLessonsThumbnails(object : OnDataLoadedCallback<NHKLessonsThumbnailsResponse> {
            override fun onSuccess(data: NHKLessonsThumbnailsResponse) {
                data.nhkLessonsThumbnails?.let { pageView.showLessonsDataWithThumbnails(it) }
            }

            override fun onFailed(exception: Exception) {
                TODO()
            }
        })
    }
}

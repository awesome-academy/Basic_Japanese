package com.sun.basic_japanese.audiolessons.lessonsdescription

import android.graphics.drawable.Drawable
import com.sun.basic_japanese.base.BasePresenter
import com.sun.basic_japanese.base.BaseView
import com.sun.basic_japanese.data.model.NHKLesson

interface AudioLessonsContract {
    interface View : BaseView {
        fun setLessonsData(lessonItems: List<NHKLesson>)
        fun showLessonsDataWithThumbnails(lessonsThumbnails: List<Drawable>)
    }

    interface Presenter : BasePresenter {
        fun getLessonsData()
    }
}

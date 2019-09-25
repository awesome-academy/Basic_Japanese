package com.sun.basic_japanese.audiolessons.lessondetail

import android.graphics.drawable.Drawable
import com.sun.basic_japanese.base.BasePresenter
import com.sun.basic_japanese.base.BaseView
import com.sun.basic_japanese.data.model.Dialogue
import com.sun.basic_japanese.data.model.NHKAudioResponse

interface AudioLessonDetailContract {
    interface View : BaseView {
        fun showDialogueData(dialogues: List<Dialogue>)
        fun setLessonThumbnail(thumbnail: Drawable)
        fun setupAudioPlayer(lessonAudio: NHKAudioResponse)
    }

    interface Presenter : BasePresenter {
        fun setDialogue(dialogueString: String)
        fun getLessonThumbnail(imageFileName: String)
        fun getLessonAudio(audioFileName: String)
    }
}

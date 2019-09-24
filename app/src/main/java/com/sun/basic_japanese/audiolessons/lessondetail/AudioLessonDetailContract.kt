package com.sun.basic_japanese.audiolessons.lessondetail

import com.sun.basic_japanese.base.BasePresenter
import com.sun.basic_japanese.base.BaseView
import com.sun.basic_japanese.data.model.DialogueWithAudio

interface AudioLessonDetailContract {
    interface View : BaseView {
        fun showDialogueData(dialogues: List<DialogueWithAudio>)
    }

    interface Presenter : BasePresenter {
        fun setDialogue(dialogueString: String)
    }
}

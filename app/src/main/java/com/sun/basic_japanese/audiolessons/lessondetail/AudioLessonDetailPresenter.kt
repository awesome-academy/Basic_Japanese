package com.sun.basic_japanese.audiolessons.lessondetail

import com.sun.basic_japanese.data.model.Dialogue
import com.sun.basic_japanese.data.model.DialogueWithAudio
import com.sun.basic_japanese.data.repository.NHKLessonRepository
import org.json.JSONArray

class AudioLessonDetailPresenter(
    private val audioLessonDetailView: AudioLessonDetailContract.View,
    private val nhkLessonRepository: NHKLessonRepository
) : AudioLessonDetailContract.Presenter {

    override fun setDialogue(dialogueString: String) {
        val jSONArray = JSONArray(dialogueString)
        val dialogs = mutableListOf<DialogueWithAudio>()
        for (index in 0 until jSONArray.length()) {
            val dialogue = Dialogue(
                jSONArray.getJSONObject(index)
            )
            dialogs.add(DialogueWithAudio(dialogue, null))
        }
        audioLessonDetailView.showDialogueData(dialogs)
    }
}

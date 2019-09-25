package com.sun.basic_japanese.audiolessons.lessondetail

import com.sun.basic_japanese.data.model.Dialogue
import com.sun.basic_japanese.data.model.LessonThumbnailResponse
import com.sun.basic_japanese.data.model.NHKAudioResponse
import com.sun.basic_japanese.data.source.OnDataLoadedCallback
import com.sun.basic_japanese.data.source.local.LessonLocalAsset
import org.json.JSONArray

class AudioLessonDetailPresenter(
    private val audioLessonDetailView: AudioLessonDetailContract.View,
    private val nhkLessonAssetDataSource: LessonLocalAsset
) : AudioLessonDetailContract.Presenter {

    override fun setDialogue(dialogueString: String) {
        val jSONArray = JSONArray(dialogueString)
        val dialogs = mutableListOf<Dialogue>()
        for (index in 0 until jSONArray.length()) {
            dialogs.add(
                Dialogue(
                    jSONArray.getJSONObject(index)
                )
            )
        }
        audioLessonDetailView.showDialogueData(dialogs)
    }

    override fun getLessonThumbnail(imageFileName: String) {
        nhkLessonAssetDataSource.getLessonThumbnail(
            imageFileName,
            object : OnDataLoadedCallback<LessonThumbnailResponse> {
                override fun onSuccess(data: LessonThumbnailResponse) {
                    audioLessonDetailView.setLessonThumbnail(data.thumbnail)
                }

                override fun onFailed(exception: Exception) {
                    TODO()
                }
            })
    }

    override fun getLessonAudio(audioFileName: String) {
        nhkLessonAssetDataSource.getLessonAudio(audioFileName,
            object : OnDataLoadedCallback<NHKAudioResponse> {
                override fun onSuccess(data: NHKAudioResponse) {
                    audioLessonDetailView.setupAudioPlayer(data)
                }

                override fun onFailed(exception: Exception) {
                    TODO()
                }
            })
    }
}

package com.sun.basic_japanese.flashcard.eachpage

import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.constants.BasicJapaneseConstants.EMPTY_STRING
import com.sun.basic_japanese.constants.BasicJapaneseConstants.FLASHCARD
import com.sun.basic_japanese.constants.BasicJapaneseConstants.FLASHCARD_ANIMATION_DELAY
import com.sun.basic_japanese.constants.BasicJapaneseConstants.FORGOTTEN
import com.sun.basic_japanese.constants.BasicJapaneseConstants.HIRAGANA
import com.sun.basic_japanese.constants.BasicJapaneseConstants.KATAKANA
import com.sun.basic_japanese.constants.BasicJapaneseConstants.REMEMBERED
import com.sun.basic_japanese.data.model.AlphabetAudioResponse
import com.sun.basic_japanese.data.model.FlashCardMessage
import com.sun.basic_japanese.data.repository.AlphabetRepository
import com.sun.basic_japanese.data.source.local.AlphabetLocalAsset
import com.sun.basic_japanese.data.source.local.AlphabetLocalDataSource
import com.sun.basic_japanese.data.source.local.AppDatabase
import com.sun.basic_japanese.data.source.local.AssetManager
import kotlinx.android.synthetic.main.fragment_page_flash_card.*

class FlashCardPageFragment : Fragment(), FlashCardPageContract.View {

    private var flashCardPresenter: FlashCardPageContract.Presenter? = null
    private var flashCard: FlashCardMessage? = FlashCardMessage()
    private val audioPlayer: MediaPlayer by lazy { MediaPlayer() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { flashCard = it.getParcelable(FLASHCARD) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_page_flash_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        flashCard?.alphabet?.audio?.let { flashCardPresenter?.getAlphabetAudio(it) }
        showFlashCard()
        setEventClick()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed) startWordAnimation()
    }

    override fun showAlphabetRememberChanged() {
        setWordRememberStatus()
    }

    override fun setupAudioPlayer(alphabetAudio: AlphabetAudioResponse) {
        audioPlayer.apply {
            reset()
            setDataSource(
                alphabetAudio.audioDescriptor.fileDescriptor,
                alphabetAudio.audioDescriptor.startOffset,
                alphabetAudio.audioDescriptor.length
            )
            prepare()
        }
    }

    private fun initPresenter() {
        context?.let {
            val alphabetRepository =
                AlphabetRepository.getInstance(AlphabetLocalDataSource(AppDatabase.getInstance(it)))
            val alphabetAssetDataSource =
                AlphabetLocalAsset.getInstance(AssetManager.getInstance(it))
            flashCardPresenter = FlashCardPagePresenter(this, alphabetRepository, alphabetAssetDataSource)
        }
    }

    private fun startWordAnimation() {
        animatedViewJapanese.startDrawAnimation(FLASHCARD_ANIMATION_DELAY)
    }

    private fun showFlashCard() {
        textWordInternational?.text = flashCard?.alphabet?.romaji
        if (activity != null) displayWordAnimation()
        setWordRememberStatus()
    }

    private fun setWordRememberStatus() {
        switchRemember.isChecked = when (flashCard?.alphabet?.remember) {
            FORGOTTEN -> false
            REMEMBERED -> true
            else -> false
        }
    }

    private fun displayWordAnimation() {
        flashCard?.alphabet?.apply {
            val type = when (flashCard?.type) {
                HIRAGANA -> hiragana
                KATAKANA -> katakana
                else -> EMPTY_STRING
            }
            context?.let {
                animatedViewJapanese.loadSVGFile(AppDatabase.getInstance(it).getStrokeOrder(type))
            }
        }
    }

    private fun setEventClick() {
        buttonPlayAudio.setOnClickListener {
            audioPlayer.start()
        }
        switchRemember.setOnClickListener {
            flashCard?.alphabet?.let {
                it.remember = if (switchRemember.isChecked) REMEMBERED else FORGOTTEN
                flashCardPresenter?.updateAlphabetRemember(it)
            }
        }
        animatedViewJapanese.setOnClickListener {
            startWordAnimation()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(flashCard: FlashCardMessage) =
            FlashCardPageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(FLASHCARD, flashCard)
                }
            }
    }
}

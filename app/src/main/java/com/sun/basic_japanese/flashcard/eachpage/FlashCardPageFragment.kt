package com.sun.basic_japanese.flashcard.eachpage

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
import com.sun.basic_japanese.data.model.FlashCardMessage
import com.sun.basic_japanese.data.repository.AlphabetRepository
import com.sun.basic_japanese.data.source.local.AlphabetLocalDataSource
import com.sun.basic_japanese.data.source.local.AppDatabase
import kotlinx.android.synthetic.main.fragment_page_flash_card.*

class FlashCardPageFragment : Fragment(), FlashCardPageContract.View {

    private var flashCardPresenter: FlashCardPageContract.Presenter? = null
    private var flashCard = FlashCardMessage()

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
        showFlashCard()
        setEventClick()
    }

    override fun showAlphabetRememberChanged() {
        setWordRememberStatus()
    }

    private fun initPresenter() {
        context?.let {
            val alphabetRepository =
                AlphabetRepository.getInstance(AlphabetLocalDataSource(AppDatabase.getInstance(it)))
            flashCardPresenter = FlashCardPagePresenter(this, alphabetRepository)
        }
    }

    private fun showFlashCard() {
        flashCard.apply {
            textWordInternational?.text = alphabet?.romaji
            if (activity != null) displayWordAnimation()
            setWordRememberStatus()
        }
    }

    private fun setWordRememberStatus() {
        switchRemember.isChecked = when (flashCard.alphabet?.remember) {
            FORGOTTEN -> false
            REMEMBERED -> true
            else -> false
        }
    }

    private fun displayWordAnimation() {
        flashCard.alphabet?.apply {
            val type = when (flashCard.type) {
                HIRAGANA -> hiragana
                KATAKANA -> katakana
                else -> EMPTY_STRING
            }
            context?.let {
                animatedViewJapanese.loadSVGFile(AppDatabase.getInstance(it).getStrokeOrder(type))
                animatedViewJapanese.startDrawAnimation(FLASHCARD_ANIMATION_DELAY)
            }
        }
    }

    private fun setEventClick() {
        buttonPlayAudio.setOnClickListener {
            TODO()
        }
        switchRemember.setOnClickListener {
            flashCard.alphabet?.let {
                it.remember = if (switchRemember.isChecked) REMEMBERED else FORGOTTEN
                flashCardPresenter?.updateAlphabetRemember(it)
            }
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

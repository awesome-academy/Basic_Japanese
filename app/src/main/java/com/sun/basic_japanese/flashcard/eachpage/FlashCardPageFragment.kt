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
import com.sun.basic_japanese.constants.BasicJapaneseConstants.HIRAGANA
import com.sun.basic_japanese.constants.BasicJapaneseConstants.KATAKANA
import com.sun.basic_japanese.data.model.FlashCardMessage
import com.sun.basic_japanese.data.source.local.AppDatabase
import kotlinx.android.synthetic.main.fragment_page_flash_card.*

class FlashCardPageFragment : Fragment(), FlashCardPageContract.View {

    private var presenter: FlashCardPageContract.Presenter? = null
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
    }

    private fun initPresenter() {
        presenter = FlashCardPagePresenter(this)
    }

    private fun showFlashCard() {
        flashCard.apply {
            textWordInternational?.text = alphabet?.romaji
            if (activity != null) displayWordAnimation()
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

package com.sun.basic_japanese.flashcard.allpages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.constants.BasicJapaneseConstants.EMPTY_STRING
import com.sun.basic_japanese.constants.BasicJapaneseConstants.FLASHCARD
import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.model.FlashCardMessage
import com.sun.basic_japanese.data.model.FlashCardsMessage
import com.sun.basic_japanese.flashcard.eachpage.FlashCardPageFragment
import com.sun.basic_japanese.flashcard.adapter.FlashCardPagerAdapter
import kotlinx.android.synthetic.main.fragment_flash_card.*

class FlashCardFragment : BaseFragment(), FlashCardContract.View {

    private var flashCardPresenter: FlashCardContract.Presenter? = null
    private var currentPosition = START_POSITION
    private var alphabetItems = listOf<Alphabet?>()
    private var type: String? = EMPTY_STRING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val flashCards = arguments?.getParcelable<FlashCardsMessage>(FLASHCARD)
        flashCards?.let {
            currentPosition = it.currentPosition
            alphabetItems = it.alphabet
            type = it.type
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_flash_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        showFlashCards()
    }

    private fun initPresenter() {
        flashCardPresenter = FlashCardPresenter(this)
    }

    private fun showFlashCards() {
        val flashCardFragments = mutableListOf<FlashCardPageFragment>()
        alphabetItems.forEach {
            val flashCard = FlashCardMessage(it, type)
            flashCardFragments.add(FlashCardPageFragment.newInstance(flashCard))
        }

        fragmentManager?.let {
            viewPagerFlashCard?.adapter = FlashCardPagerAdapter(it, flashCardFragments)
            viewPagerFlashCard?.currentItem = currentPosition
        }
    }

    companion object {

        private const val START_POSITION = 0

        @JvmStatic
        fun newInstance(flashCards: FlashCardsMessage) =
            FlashCardFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(FLASHCARD, flashCards)
                }
            }
    }
}

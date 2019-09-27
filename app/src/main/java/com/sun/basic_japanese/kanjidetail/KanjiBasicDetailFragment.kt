package com.sun.basic_japanese.kanjidetail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.base.FragmentInteractionListener
import com.sun.basic_japanese.data.model.Example
import com.sun.basic_japanese.data.model.KanjiBasic
import com.sun.basic_japanese.data.model.KanjiBasicMessage
import com.sun.basic_japanese.data.repository.KanjiRepository
import com.sun.basic_japanese.data.source.local.AppDatabase
import com.sun.basic_japanese.data.source.local.KanjiLocalDataSource
import com.sun.basic_japanese.data.source.remote.KanjiRemoteDataSource
import com.sun.basic_japanese.util.Constants
import com.sun.basic_japanese.util.Extensions.parseExamples
import com.sun.basic_japanese.util.Extensions.setHtmlText
import com.sun.basic_japanese.util.Extensions.setImage
import com.sun.basic_japanese.util.Extensions.showToast
import kotlinx.android.synthetic.main.bottom_navigation.*
import kotlinx.android.synthetic.main.fragment_kanji_detail.*

class KanjiBasicDetailFragment @SuppressLint("ValidFragment") private constructor() :
    BaseFragment(),
    View.OnClickListener,
    KanjiDetailContract.View {

    private val local by lazy {
        context?.let { KanjiLocalDataSource.getInstance(AppDatabase.getInstance(it)) }
    }
    private val remote by lazy {
        KanjiRemoteDataSource.getInstance()
    }
    private val kanjiRepository by lazy {
        local?.let { KanjiRepository.getInstance(it, remote) }
    }
    private val kanjiDetailPresenter by lazy {
        kanjiRepository?.let { KanjiDetailPresenter(this, it) }
    }
    private val message by lazy {
        arguments?.getParcelable<KanjiBasicMessage>(KANJI_DETAIL)
    }
    private val kanjiBasicList by lazy {
        message?.kanjiBasicList
    }
    private val kanjiExampleRecyclerAdapter = KanjiExampleRecyclerAdapter()

    private var listener: OnKanjiBasicDetailFragmentInteractionListener? = null
    private var currentIndex = 1
    private var count = 0
    private var currentKanji: KanjiBasic? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnKanjiBasicDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("${context.toString()} $ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_kanji_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        message?.let {
            initView()
            count = kanjiBasicList?.size ?: 0
            showKanjiDetail(it.currentPosition)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imageKanjiStrokeOrder -> imageKanjiStrokeOrder?.startDrawAnimation()

            R.id.buttonKanjiFavorite -> updateFavoriteKanji()

            R.id.buttonLessonPrevious -> {
                listener?.scroll(ScrollView.FOCUS_UP)
                showKanjiDetail(currentIndex - 1)
            }

            R.id.buttonLessonNext -> {
                listener?.scroll(ScrollView.FOCUS_UP)
                showKanjiDetail(currentIndex + 1)
            }
        }
    }

    override fun showStrokeOrderAnimation(input: String) {
        imageKanjiStrokeOrder?.apply {
            loadSVGFile(input)
            startDrawAnimation()
        }
    }

    override fun showError(message: String) {
        context?.showToast(message)
    }

    private fun initView() {
        buttonLessonSelect?.apply {
            text = resources.getString(R.string.title_practice)
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        recyclerKanjiExample?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = kanjiExampleRecyclerAdapter
        }

        buttonLessonSelect.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        buttonLessonPrevious.setOnClickListener(this)
        buttonLessonNext.setOnClickListener(this)
        buttonKanjiFavorite.setOnClickListener(this)
        imageKanjiStrokeOrder.setOnClickListener(this)
        bottomNavigation.isNestedScrollingEnabled = false
    }

    private fun showKanjiDetail(index: Int) {
        currentIndex = index
        currentKanji = kanjiBasicList?.get(currentIndex)
        setDetailKanji()

        buttonLessonPrevious.visibility =
            if (currentIndex > 0) View.VISIBLE else View.INVISIBLE
        buttonLessonNext.visibility =
            if (currentIndex < count - 1) View.VISIBLE else View.INVISIBLE
    }

    private fun setDetailKanji() {
        if (currentKanji?.favorite == Constants.TRUE)
            buttonKanjiFavorite?.setImageResource(R.drawable.ic_favorite_24dp)
        else
            buttonKanjiFavorite?.setImageResource(R.drawable.ic_not_favorite_24dp)

        currentKanji?.apply {
            kanjiDetailPresenter?.getStrokeOrder(word)
            imageKanjiRemember?.setImage(image)
            textKanjiRemember?.setHtmlText(remember)
            textKanjiChinaMean?.text = chinaMean
            textKanjiVietMean?.text = vietMean
            textKanjiOnjomi?.text = onjomi
            textKanjiKunjomi?.text = kunjomi
            textKanjiRonjomi?.text = romajiOnjomi
            textKanjiRkunjomi?.text = romajiKunjomi
            kanjiExampleRecyclerAdapter.updateData(mutableListOf<Example>().parseExamples(example))
        }
    }

    private fun updateFavoriteKanji() {
        when (currentKanji?.favorite) {
            Constants.FALSE -> {
                currentKanji?.apply {
                    favorite = Constants.TRUE
                }?.let { kanjiDetailPresenter?.updateFavoriteKanjiBasic(it) }
                buttonKanjiFavorite?.setImageResource(
                    R.drawable.ic_favorite_24dp
                )
            }

            Constants.TRUE -> {
                currentKanji?.apply {
                    favorite = Constants.FALSE
                }?.let { kanjiDetailPresenter?.updateFavoriteKanjiBasic(it) }
                buttonKanjiFavorite?.setImageResource(
                    R.drawable.ic_not_favorite_24dp
                )
            }
        }
    }


    interface OnKanjiBasicDetailFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val KANJI_DETAIL = "Kanji detail"
        private const val ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER =
            "must implement OnKanjiBasicFragmentInteractionListener"

        @JvmStatic
        fun newInstance(message: KanjiBasicMessage) =
            KanjiBasicDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KANJI_DETAIL, message)
                }
            }
    }
}

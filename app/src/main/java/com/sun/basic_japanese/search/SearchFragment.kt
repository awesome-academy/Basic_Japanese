package com.sun.basic_japanese.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.base.FragmentInteractionListener
import com.sun.basic_japanese.data.model.KanjiAdvance
import com.sun.basic_japanese.data.model.KanjiAdvanceMessage
import com.sun.basic_japanese.data.repository.KanjiRepository
import com.sun.basic_japanese.data.source.local.AppDatabase
import com.sun.basic_japanese.data.source.local.KanjiLocalDataSource
import com.sun.basic_japanese.data.source.remote.KanjiRemoteDataSource
import com.sun.basic_japanese.kanjidetail.KanjiAdvanceDetailFragment
import com.sun.basic_japanese.util.Extensions.showToast
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment(),
    SearchContract.View,
    SearchAdapter.OnSearchItemClickListener {

    private val local by lazy {
        context?.let { KanjiLocalDataSource.getInstance(AppDatabase.getInstance(it)) }
    }
    private val remote by lazy {
        KanjiRemoteDataSource.getInstance()
    }
    private val kanjiRepository by lazy {
        local?.let { KanjiRepository.getInstance(it, remote) }
    }
    private val searchPresenter by lazy {
        kanjiRepository?.let { SearchPresenter(this, it) }
    }
    private val searchAdapter = SearchAdapter(this)
    private var listener: OnSearchFragmentInteractionListener? = null
    private var kanjiAdvanceList = listOf<KanjiAdvance>()
    private var query = ""

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSearchFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("${context.toString()} $ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("SetTextI18n")
    override fun showKanjiAdvanceData(kanjiAdvanceList: List<KanjiAdvance>) {
        this.kanjiAdvanceList = kanjiAdvanceList
        textEmpty?.text =
            "${kanjiAdvanceList.size} ${resources.getString(R.string.title_search_count)} $query"
        searchAdapter.updateData(kanjiAdvanceList)
    }

    override fun showError(message: String) {
        context?.showToast(message)
    }

    override fun showItemDetail(currentPosition: Int) {
        getNavigationManager().open(
            KanjiAdvanceDetailFragment.newInstance(
                KanjiAdvanceMessage(kanjiAdvanceList, currentPosition)
            )
        )
    }

    private fun initView() {
        recyclerSearch?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }
    }

    @SuppressLint("DefaultLocale")
    fun searchKanji(query: String) {
        this.query = query
        searchPresenter?.getKanjiAdvanceData(query.trim().toLowerCase())
    }

    interface OnSearchFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER =
            "must implement OnKanjiBasicFragmentInteractionListener"

        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}

package com.sun.basic_japanese.alphabet.eachpage

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.alphabet.adapter.AlphabetRecyclerAdapter
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.constants.BasicJapaneseConstants.ALPHABET_COLUMN_NUMBER
import com.sun.basic_japanese.constants.BasicJapaneseConstants.TYPE
import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.repository.AlphabetRepository
import com.sun.basic_japanese.data.source.local.AlphabetLocalDataSource
import com.sun.basic_japanese.data.source.local.AppDatabase
import kotlinx.android.synthetic.main.fragment_page_alphabet.*

class AlphabetPageFragment : BaseFragment(), AlphabetPageContract.View {

    private var pagePresenter: AlphabetPageContract.Presenter? = null
    private var alphabetType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run { alphabetType = getString(TYPE) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_page_alphabet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        pagePresenter?.getAlphabetData()
    }

    override fun showAlphabetData(alphabetItems: List<Alphabet?>) {
        val gridLayoutManager = GridLayoutManager(
            context,
            ALPHABET_COLUMN_NUMBER,
            LinearLayoutManager.VERTICAL,
            false
        )
        val gridAdapter = AlphabetRecyclerAdapter(alphabetItems, alphabetType)
        recyclerAlphabet.apply {
            layoutManager = gridLayoutManager
            adapter = gridAdapter
        }
    }

    override fun initPresenter() {
        context?.let {
            val alphabetRepository =
                AlphabetRepository.getInstance(AlphabetLocalDataSource(AppDatabase.getInstance(it)))
            pagePresenter = AlphabetPagePresenter(this, alphabetRepository)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(alphabetType: String?) =
            AlphabetPageFragment().apply {
                arguments = Bundle().apply {
                    putString(TYPE, alphabetType)
                }
            }
    }
}

package com.sun.basic_japanese.alphabet.allpages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.alphabet.eachpage.AlphabetPageFragment
import com.sun.basic_japanese.alphabet.adapter.PagerAdapter
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.constants.BasicJapaneseConstants.HIRAGANA
import com.sun.basic_japanese.constants.BasicJapaneseConstants.KATAKANA
import kotlinx.android.synthetic.main.fragment_alphabet.*

class AlphabetFragment : BaseFragment(), AlphabetContract.View {

    private var alphabetPresenter: AlphabetContract.Presenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_alphabet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        setupViewPager()
    }

    private fun initPresenter() {
        alphabetPresenter = AlphabetPresenter(this)
    }

    private fun setupViewPager() {
        val gridFragments = mutableListOf<AlphabetPageFragment>()
        gridFragments.apply {
            add(AlphabetPageFragment.newInstance(HIRAGANA))
            add(AlphabetPageFragment.newInstance(KATAKANA))
        }
        val tabLayoutTitles = mutableListOf<CharSequence?>().apply {
            add(resources.getString(R.string.title_hiragana))
            add(resources.getString(R.string.title_katakana))
        }
        fragmentManager?.let {
            viewPagerAlphabet.adapter = PagerAdapter(it, gridFragments, tabLayoutTitles)
        }
        tabLayoutAlphabet.setupWithViewPager(viewPagerAlphabet, true)
    }
    companion object {

        @JvmStatic
        fun newInstance() = AlphabetFragment()
    }
}

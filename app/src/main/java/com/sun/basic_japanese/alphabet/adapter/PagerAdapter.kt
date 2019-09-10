package com.sun.basic_japanese.alphabet.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.sun.basic_japanese.alphabet.eachpage.AlphabetPageFragment

class PagerAdapter(
    fragmentManager: FragmentManager,
    private val fragments: MutableList<AlphabetPageFragment>,
    private val titles: MutableList<CharSequence?>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int) = titles[position]
}

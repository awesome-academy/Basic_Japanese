package com.sun.basic_japanese.flashcard.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.sun.basic_japanese.flashcard.eachpage.FlashCardPageFragment

class FlashCardPagerAdapter(
    fragmentManager: FragmentManager,
    private val fragments: List<FlashCardPageFragment>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size
}

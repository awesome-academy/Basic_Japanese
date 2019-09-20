package com.sun.basic_japanese.home

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.alphabet.allpages.AlphabetFragment
import com.sun.basic_japanese.audiolessons.lessonsdescription.AudioLessonsFragment
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.base.FragmentInteractionListener
import com.sun.basic_japanese.kanji_advance.KanjiAdvanceFragment
import com.sun.basic_japanese.kanji_basic.KanjiBasicFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.one_line_element.view.*

class HomeFragment : BaseFragment() {

    private var listener: OnHomeFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setEventClick()
    }

    private fun setEventClick() {
        layoutAlphabet.setOnClickListener {
            getNavigationManager().open(AlphabetFragment.newInstance())
        }
        layoutAudio.setOnClickListener {
            getNavigationManager().open(AudioLessonsFragment.newInstance())
        }
        layoutKanjiBasic.setOnClickListener {
            getNavigationManager().open(KanjiBasicFragment.newInstance())
        }
        layoutKanjiAdvance.setOnClickListener {
            getNavigationManager().open(KanjiAdvanceFragment.newInstance())
        }
    }

    private fun setView() {
        setBasicView()
        setKanjiView()
        setTestView()
    }

    private fun setBasicView() {
        layoutAlphabet?.apply {
            imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_sort_by_alpha_black_24dp, null
                )
            )
            textLabel?.text = resources.getText(R.string.title_alphabet)
        }
        layoutAudio?.apply {
            imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_headset_black_24dp, null
                )
            )
            textLabel?.text = resources.getText(R.string.title_audio_learning)
        }
    }

    private fun setKanjiView() {
        layoutKanjiBasic?.apply {
            imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_text_fields_black_24dp, null
                )
            )
            textLabel?.text = resources.getText(R.string.title_kanji_basic)
        }

        layoutKanjiAdvance?.apply {
            imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_ac_unit_black_24dp, null
                )
            )
            textLabel?.text = resources.getText(R.string.title_kanji_advance)
        }

        layoutKanjiWriting?.apply {
            imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_mode_edit_black_24dp, null
                )
            )
            textLabel?.text = resources.getText(R.string.title_kanji_writing)
        }
    }

    private fun setTestView() {
        layoutTest?.apply {
            imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_school_black_24dp, null
                )
            )
            textLabel?.text = resources.getText(R.string.title_jlpt_test)
        }
        layoutTranslate?.apply {
            imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_translate_black_24dp, null
                )
            )
            textLabel?.text = resources.getText(R.string.title_translate)
        }
    }

    interface OnHomeFragmentInteractionListener : FragmentInteractionListener

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}


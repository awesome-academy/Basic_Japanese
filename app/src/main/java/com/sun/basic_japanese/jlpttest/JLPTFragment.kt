package com.sun.basic_japanese.jlpttest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.jlpttest.examdetail.JLPTQuestionFragment
import kotlinx.android.synthetic.main.fragment_jlpt_test.*
import kotlinx.android.synthetic.main.item_jlpt_exam.view.*

class JLPTFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_jlpt_test, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setN1EventClick()
        setN2EventClick()
        setN3EventClick()
        setN4EventClick()
        setN5EventClick()
    }

    private fun setN1EventClick() {
        layoutTestN1.let {
            it.textGrammar.setOnClickListener {
                showTest(TEST_13)
            }
            it.textVocabulary.setOnClickListener {
                showTest(TEST_14)
            }
            it.textKanji.setOnClickListener {
                showTest(TEST_15)
            }
        }
    }

    private fun setN2EventClick() {
        layoutTestN2.let {
            it.textGrammar.setOnClickListener {
                showTest(TEST_10)
            }
            it.textVocabulary.setOnClickListener {
                showTest(TEST_11)
            }
            it.textKanji.setOnClickListener {
                showTest(TEST_12)
            }
        }
    }

    private fun setN3EventClick() {
        layoutTestN3.let {
            it.textGrammar.setOnClickListener {
                showTest(TEST_7)
            }
            it.textVocabulary.setOnClickListener {
                showTest(TEST_8)
            }
            it.textKanji.setOnClickListener {
                showTest(TEST_9)
            }
        }
    }

    private fun setN4EventClick() {
        layoutTestN4.let {
            it.textGrammar.setOnClickListener {
                showTest(TEST_4)
            }
            it.textVocabulary.setOnClickListener {
                showTest(TEST_5)
            }
            it.textKanji.setOnClickListener {
                showTest(TEST_6)
            }
        }
    }

    private fun setN5EventClick() {
        layoutTestN5.let {
            it.textGrammar.setOnClickListener {
                showTest(TEST_1)
            }
            it.textVocabulary.setOnClickListener {
                showTest(TEST_2)
            }
            it.textKanji.setOnClickListener {
                showTest(TEST_3)
            }
        }
    }

    private fun showTest(category: String) {
        getNavigationManager().open(JLPTQuestionFragment.newInstance(category))
    }

    companion object {

        private const val TEST_1 = "1"
        private const val TEST_2 = "2"
        private const val TEST_3 = "3"
        private const val TEST_4 = "4"
        private const val TEST_5 = "5"
        private const val TEST_6 = "6"
        private const val TEST_7 = "7"
        private const val TEST_8 = "8"
        private const val TEST_9 = "9"
        private const val TEST_10 = "10"
        private const val TEST_11 = "11"
        private const val TEST_12 = "12"
        private const val TEST_13 = "13"
        private const val TEST_14 = "14"
        private const val TEST_15 = "15"

        @JvmStatic
        fun newInstance() = JLPTFragment()
    }
}

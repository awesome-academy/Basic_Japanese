package com.sun.basic_japanese.jlpttest.examdetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.constants.BasicJapaneseConstants.TEST_CATEGORY
import com.sun.basic_japanese.data.model.JLPTTest
import com.sun.basic_japanese.data.source.remote.JLPTTestRemoteDataSource
import com.sun.basic_japanese.util.Constants.CHARACTER_SPLIT_1
import kotlinx.android.synthetic.main.fragment_jlpt_question.*

class JLPTQuestionFragment : BaseFragment(), JLPTQuestionContract.View {

    //Category from 0 to 14 match 1 in 15 type of exam: N1-grammar, N1-kanji, etc.
    private val category by lazy { arguments?.getString(TEST_CATEGORY) }

    private var jLPTExam: List<JLPTTest>? = null
    private var currentQuestion = START_QUESTION
    private var pickedAnswer = 0

    private val jLPTRemote by lazy {
        JLPTTestRemoteDataSource.getInstance()
    }

    private val jLPTQuestionPresenter by lazy {
        JLPTQuestionPresenter(this, jLPTRemote)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_jlpt_question, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEventClick()
        category?.let { jLPTQuestionPresenter.getJLPTData(it) }
    }

    override fun showJLPTData(jLPTExam: List<JLPTTest>) {
        this.jLPTExam = jLPTExam
        displayQuestion(jLPTExam[0])
    }

    override fun showResult(status: Boolean) {
        var wrongAnswerBackground: Drawable? = null
        var correctAnswerBackground: Drawable? = null
        context?.let {
            wrongAnswerBackground = ContextCompat.getDrawable(it, R.drawable.wrong_answer_background)
            correctAnswerBackground = ContextCompat.getDrawable(it, R.drawable.correct_answer_background)
        }

        when (jLPTExam?.get(currentQuestion)?.correct?.toInt()) {
            ANSWER_1 -> textAnswer1?.background = correctAnswerBackground
            ANSWER_2 -> textAnswer2?.background = correctAnswerBackground
            ANSWER_3 -> textAnswer3?.background = correctAnswerBackground
            ANSWER_4 -> textAnswer4?.background = correctAnswerBackground
        }

        if (!status) {
            when (pickedAnswer) {
                ANSWER_1 -> textAnswer1?.background = wrongAnswerBackground
                ANSWER_2 -> textAnswer2?.background = wrongAnswerBackground
                ANSWER_3 -> textAnswer3?.background = wrongAnswerBackground
                ANSWER_4 -> textAnswer4?.background = wrongAnswerBackground
            }
        }
    }

    private fun displayQuestion(question: JLPTTest) {
        textQuestionNumber?.text = (currentQuestion + 1).toString()
        textQuestionDetail?.text = question.question
        displayAnswers(question)
        resetAnswersBackground()
        hideNextButton()
    }

    private fun resetAnswersBackground() {
        context?.let {
            val defaultAnswerBackground =
                ContextCompat.getDrawable(it, R.drawable.fragment_background)
            textAnswer1?.background = defaultAnswerBackground
            textAnswer2?.background = defaultAnswerBackground
            textAnswer3?.background = defaultAnswerBackground
            textAnswer4?.background = defaultAnswerBackground
        }
        textAnswer1.isEnabled = true
        textAnswer2.isEnabled = true
        textAnswer3.isEnabled = true
        textAnswer4.isEnabled = true
    }

    private fun displayAnswers(question: JLPTTest) {
        val answers = question.answer.split(CHARACTER_SPLIT_1)
        textAnswer1?.text = answers[ANSWER_1]
        textAnswer2?.text = answers[ANSWER_2]
        textAnswer3?.text = answers[ANSWER_3]
        textAnswer4?.text = answers[ANSWER_4]
    }

    private fun hideNextButton() {
        buttonNextQuestion?.visibility = View.INVISIBLE
    }

    private fun displayNextButton() {
        buttonNextQuestion?.visibility = View.VISIBLE
        textAnswer1.isEnabled = false
        textAnswer2.isEnabled = false
        textAnswer3.isEnabled = false
        textAnswer4.isEnabled = false
    }

    private fun finishExam() {
        TODO()
    }

    private fun evaluate(answerNumber: Int) {
        pickedAnswer = answerNumber
        jLPTQuestionPresenter.evaluate(currentQuestion, answerNumber)
        displayNextButton()
    }

    private fun setEventClick() {
        buttonNextQuestion?.setOnClickListener {
            if (currentQuestion == jLPTExam?.size?.minus(1)) finishExam()
            else {
                currentQuestion++
                jLPTExam?.get(currentQuestion)?.let { displayQuestion(it) }
            }
        }
        textAnswer1?.setOnClickListener {
            evaluate(ANSWER_1)
        }
        textAnswer2?.setOnClickListener {
            evaluate(ANSWER_2)
        }
        textAnswer3?.setOnClickListener {
            evaluate(ANSWER_3)
        }
        textAnswer4?.setOnClickListener {
            evaluate(ANSWER_4)
        }
    }

    companion object {

        private const val ANSWER_1 = 0
        private const val ANSWER_2 = 1
        private const val ANSWER_3 = 2
        private const val ANSWER_4 = 3

        private const val START_QUESTION = 0

        @JvmStatic
        fun newInstance(category: String) =
            JLPTQuestionFragment().apply {
                arguments = Bundle().apply { putString(TEST_CATEGORY, category) }
            }
    }
}

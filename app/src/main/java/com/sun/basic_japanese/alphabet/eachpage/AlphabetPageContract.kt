package com.sun.basic_japanese.alphabet.eachpage

import com.sun.basic_japanese.base.BasePresenter
import com.sun.basic_japanese.base.BaseView
import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.repository.AlphabetRepository

interface AlphabetPageContract {

    interface View : BaseView{
        fun showAlphabetData(alphabetItems: List<Alphabet?>)
    }

    interface Presenter : BasePresenter {
        fun getAlphabetData()
    }
}

package com.sun.basic_japanese

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.base.HasNavigationManager
import com.sun.basic_japanese.base.NavigationManager
import com.sun.basic_japanese.home.HomeFragment
import com.sun.basic_japanese.kanjiadvance.KanjiAdvanceFragment
import com.sun.basic_japanese.kanjibasic.KanjiBasicFragment
import com.sun.basic_japanese.kanjidetail.KanjiAdvanceDetailFragment
import com.sun.basic_japanese.kanjidetail.KanjiBasicDetailFragment
import com.sun.basic_japanese.search.SearchFragment
import com.sun.basic_japanese.util.Constants
import com.sun.basic_japanese.util.Extensions.showToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    HomeFragment.OnHomeFragmentInteractionListener,
    KanjiBasicFragment.OnKanjiBasicFragmentInteractionListener,
    KanjiAdvanceFragment.OnKanjiAdvanceFragmentInteractionListener,
    KanjiBasicDetailFragment.OnKanjiBasicDetailFragmentInteractionListener,
    KanjiAdvanceDetailFragment.OnKanjiDetailFragmentInteractionListener,
    SearchFragment.OnSearchFragmentInteractionListener,
    HasNavigationManager,
    SearchView.OnQueryTextListener {

    private var searchItem: MenuItem? = null
    private lateinit var navigationManager: NavigationManager
    private lateinit var currentFragment: BaseFragment
    private lateinit var inputMethodManager: InputMethodManager
    private lateinit var searchManager: SearchManager
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        navigationManager = NavigationManager(
            supportFragmentManager,
            R.id.mainContainer
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: navigationManager.openAsRoot(HomeFragment.newInstance())
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        searchItem = menu?.findItem(R.id.actionSearch)
        searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = searchItem?.actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
            setOnQueryTextListener(this@MainActivity)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.actionSearch && currentFragment !is SearchFragment) {
            navigationManager.open(SearchFragment.newInstance())
        }
        return true
    }

    @SuppressLint("DefaultLocale")
    override fun onQueryTextSubmit(query: String): Boolean {
        searchItem?.collapseActionView()
        return true
    }

    @SuppressLint("DefaultLocale")
    override fun onQueryTextChange(query: String): Boolean {
        val keyWord = query.trim().toLowerCase()
        if (keyWord.length >= Constants.MIN_QUERY_SEARCH)
            searchKanji(query)
        return true
    }

    override fun setToolbarTitle(title: String) {
        collapsingToolbar?.title = title
    }

    override fun setToolbarVisibility(show: Boolean) {
        collapsingToolbar?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun setCurrentFragment(fragment: BaseFragment) {
        currentFragment = fragment
    }

    override fun scroll(input: Int) {
        mainContainer.fullScroll(input)
    }

    override fun provideNavigationManager() = navigationManager

    private fun initView() {
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
        setSupportActionBar(toolBar)
        supportActionBar?.title = resources.getString(R.string.title_home)
    }

    private fun searchKanji(query: String) {
        if (currentFragment is SearchFragment)
            (currentFragment as SearchFragment).searchKanji(query)
    }
}

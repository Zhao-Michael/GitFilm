package venerealulcer

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import douban.Douban
import org.jetbrains.anko.find
import util.*


class SearchActivity : BaseActivity(), FloatingSearchView.OnSearchListener, FloatingSearchView.OnQueryChangeListener {

    override val mLayout: Int = R.layout.activity_search
    private val mSearchView by lazy { find<FloatingSearchView>(R.id.floating_search_view) }
    private val mRecyclerView by lazy { find<RecyclerView>(R.id.mRecyclerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSwipeLayout.setColorSchemeColors(getPrimaryColor())

        mSwipeLayout.DisEnable()

        mRecyclerView.layoutManager = GridLayoutManager(this, 1)

        mSearchView.setOnQueryChangeListener(this)

        mSearchView.setOnSearchListener(this)

        mSearchView.setOnHomeActionClickListener { finish() }
    }

    override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) = Unit

    override fun onSearchAction(currentQuery: String?) {
        val query = currentQuery.toString()
        if (query.isBlank()) {
            mRecyclerView.adapter = null
        } else {
            mSwipeLayout.Enable()
            mSwipeLayout.ShowRefresh()
            Rx.get {
                Douban.getSearchFilmList(query)
            }.set {
                mRecyclerView.FilmAdapter = it
            }.com {
                mSwipeLayout.DisEnable()
                mSwipeLayout.HideRefresh()
            }
        }
    }

    override fun onSearchTextChanged(oldQuery: String?, newQuery: String?) {
        if (newQuery == null || newQuery.isBlank()) {
            mRecyclerView.adapter = null
        } else {
            Rx.get {
                Douban.getSearchBrief(newQuery)
            }.set {
                if (newQuery == mSearchView.query)
                    mRecyclerView.BriefAdapter = it
            }
        }
    }


}
package com.graphtech.giserap.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.graphtech.giserap.R
import com.graphtech.giserap.adapter.SearchUsernameAdapter
import com.graphtech.giserap.model.SearchItem
import com.graphtech.giserap.model.SearchResponse
import com.graphtech.giserap.presenter.SearchUsernamePresenter
import com.graphtech.giserap.view.SearchUsernameView
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast
import org.json.JSONObject

class SearchActivity : AppCompatActivity(), SearchUsernameView {

    private lateinit var searchPresenter: SearchUsernamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // setupActionBar
        setupActionBar()

        intentMainActivity.setOnClickListener {
            finish()
        }

        // setquery searchView
        val getQuery = intent.getStringExtra("query")

        // declare presenter
        searchPresenter = SearchUsernamePresenter(this)
        searchPresenter.getSearchResult(getQuery)

        // text search result
        svSearchResult.setQuery(getQuery, false)
        svSearchResult.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                rvSearchResult.visibility = View.GONE
                tvNullData.visibility = View.GONE
                searchPresenter.getSearchResult(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }



    override fun onShowLoading() {
        shimmerSearchUser.visibility = View.VISIBLE
        shimmerSearchUser.startShimmer()
    }

    override fun onHideLoading() {
        shimmerSearchUser.visibility = View.GONE
        shimmerSearchUser.stopShimmer()
    }

    override fun onShowNothing(message: String) {
        tvNullData.visibility = View.VISIBLE
        tvNullData.text = message
        rvSearchResult.visibility = View.GONE
    }

    override fun onHideNothing() {
        tvNullData.visibility = View.GONE
        rvSearchResult.visibility = View.VISIBLE
    }

    override fun onSuccessGetSearch(data: List<SearchItem?>?) {
        rvSearchResult.layoutManager = LinearLayoutManager(this)
        rvSearchResult.adapter = SearchUsernameAdapter(this, data)
    }

    override fun onErrorGetSearch(message: String) {
        toast(message)
    }
}

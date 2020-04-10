package com.dedi.subfootball.feature.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.dedi.subfootball.R
import com.dedi.subfootball.adapter.MatchAdapter
import com.dedi.subfootball.api.ApiService
import com.dedi.subfootball.api.MyApi
import com.dedi.subfootball.model.ResultsItemMatch
import com.dedi.subfootball.repository.MatchRepositoryImpl
import com.dedi.subfootball.rx.AppSchedulerProvider
import kotlinx.android.synthetic.main.abc_activity_chooser_view.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), SearchView.View {
    private var matchLists : MutableList<ResultsItemMatch> = mutableListOf()
    lateinit var mPresenter: SearchView.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setTitle("Search")
        val query = intent.getStringExtra("query")
        Log.v("test", query)
        val service = ApiService.getClient().create(MyApi::class.java)
        val request = MatchRepositoryImpl(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = SearchPresenter(this, request,scheduler)
        mPresenter.searchMatch(query)
    }

    override fun showLoading() {
        mainProgressBar.visibility = View.VISIBLE
        rvSearch.visibility = View.GONE
    }

    override fun hideLoading() {
        mainProgressBar.visibility = View.GONE
        rvSearch.visibility = View.VISIBLE
    }

    override fun showSearch(data: List<ResultsItemMatch>) {
        matchLists.clear()
        matchLists.addAll(data)
        val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        rvSearch.layoutManager = layoutManager
        rvSearch.adapter = MatchAdapter(data, this)
    }
}
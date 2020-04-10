package com.dedi.subfootball.feature.last


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.dedi.subfootball.R
import com.dedi.subfootball.adapter.MatchAdapter
import com.dedi.subfootball.api.ApiService
import com.dedi.subfootball.api.MyApi
import com.dedi.subfootball.model.ResultsItemMatch
import com.dedi.subfootball.repository.MatchRepositoryImpl
import com.dedi.subfootball.rx.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_last.*


class LastMatchFragment : Fragment(), LastView.View {
    lateinit var mPresenter : LastPresenter
    private var matchLists : MutableList<ResultsItemMatch> = mutableListOf()
    lateinit var leagueName : String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_last, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val service = ApiService.getClient().create(MyApi::class.java)
        println("Retrofit "+service)
        val request = MatchRepositoryImpl(service)
        println("requestnya "+request)
        val scheduler = AppSchedulerProvider()
        mPresenter = LastPresenter(this, request,scheduler)
        mPresenter.getLastMatchData()
        val spinnerItems = resources.getStringArray(R.array.leagueArray)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinnerLast.adapter = spinnerAdapter

        spinnerLast.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinnerLast.selectedItem.toString()
                when(leagueName){
                    "English Premier League" -> mPresenter.getLastMatchData("4328")
                    "German Bundesliga" -> mPresenter.getLastMatchData("4331")
                    "Italian Serie A" -> mPresenter.getLastMatchData("4332")
                    "French Ligue 1" -> mPresenter.getLastMatchData("4334")
                    "Spanish La Liga" -> mPresenter.getLastMatchData("4335")
                    "Netherlands Eredivisie" -> mPresenter.getLastMatchData("4337")
                    else -> mPresenter.getLastMatchData()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun showLastList(data: List<ResultsItemMatch>) {
        matchLists.clear()
        matchLists.addAll(data)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvLast.layoutManager = layoutManager
        rvLast.adapter = activity?.let { MatchAdapter(data, it) }
    }

    override fun onDestroy() {
        super.onDestroy()
            mPresenter.onDestroyPresenter()
    }
}



package com.dedi.subfootball.feature.next

import android.support.v4.app.Fragment
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_next.*

class NextMatchFragment : Fragment(), NextView.View {


    lateinit var nextPresenter: NextPresenter
    private var matchLists : MutableList<ResultsItemMatch> = mutableListOf()

    lateinit var leagueName : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        val service = ApiService.getClient().create(MyApi::class.java)
        println("Retrofit "+service)
        val request = MatchRepositoryImpl(service)
        println("requestnya "+request)
        val scheduler = AppSchedulerProvider()
        nextPresenter = NextPresenter(this, request,scheduler)
        nextPresenter.getNextMatchData()
        val spinnerItems = resources.getStringArray(R.array.leagueArray)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinerNext.adapter = spinnerAdapter

        spinerNext.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinerNext.selectedItem.toString()
                when(leagueName){
                    "English Premier League" -> nextPresenter.getNextMatchData("4328")
                    "German Bundesliga" -> nextPresenter.getNextMatchData("4331")
                    "Italian Serie A" -> nextPresenter.getNextMatchData("4332")
                    "French Ligue 1" -> nextPresenter.getNextMatchData("4334")
                    "Spanish La Liga" -> nextPresenter.getNextMatchData("4335")
                    "Netherlands Eredivisie" -> nextPresenter.getNextMatchData("4337")
                    else -> nextPresenter.getNextMatchData()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun showNextList(data: List<ResultsItemMatch>) {
        matchLists.clear()
        matchLists.addAll(data)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvNext.layoutManager = layoutManager
        rvNext.adapter = activity?.let { MatchAdapter(data, it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        nextPresenter.onDestroyPresenter()
    }
}
package com.dedi.subfootball.feature.teams


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*

import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.dedi.subfootball.R
import com.dedi.subfootball.adapter.TeamsAdapter
import com.dedi.subfootball.api.ApiService
import com.dedi.subfootball.api.MyApi
import com.dedi.subfootball.model.TeamsItem
import com.dedi.subfootball.repository.TeamRepositoryImpl
import com.dedi.subfootball.rx.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_teams.*


class TeamsFragment : Fragment() , TeamsView.View{
    lateinit var leagueName : String
    lateinit var mPresenter : TeamsView.Presenter

    private var teamLists : MutableList<TeamsItem> = mutableListOf()

    override fun showTeams(teamList: List<TeamsItem>) {
        teamLists.clear()
        teamLists.addAll(teamList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvTeams.layoutManager = layoutManager
        rvTeams.adapter = TeamsAdapter(teamLists, context)
    }

    override fun showLoading() {
        mainProgressBar.visibility = View.VISIBLE
        rvTeams.visibility = View.GONE
    }

    override fun hideLoading() {
        mainProgressBar.visibility = View.GONE
        rvTeams.visibility = View.VISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val service = ApiService.getClient().create(MyApi::class.java)
        val request = TeamRepositoryImpl(service)
        val scheduler = AppSchedulerProvider()
        setHasOptionsMenu(true)
        mPresenter = TeamsPresenter(this, request, scheduler)
        mPresenter.getTeamData("4328")
        val spinnerItems = resources.getStringArray(R.array.leagueArray)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spTeam.adapter = spinnerAdapter
        spTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spTeam.selectedItem.toString()
                when(leagueName){
                    "English Premier League" -> mPresenter.getTeamData("4328")
                    "German Bundesliga" -> mPresenter.getTeamData("4331")
                    "Italian Serie A" -> mPresenter.getTeamData("4332")
                    "French Ligue 1" -> mPresenter.getTeamData("4334")
                    "Spanish La Liga" -> mPresenter.getTeamData("4335")
                    "Netherlands Eredivisie" -> mPresenter.getTeamData("4337")
                    else -> mPresenter.getTeamData("4328")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView?
        searchView?.queryHint = "Search team"

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mPresenter.searchTeam(newText)
                return false
            }
        })

        searchView?.setOnCloseListener(object: SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                mPresenter.getTeamData("4328")
                return true
            }
        })
    }

}

package com.dedi.subfootball.feature.favoriteteam


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dedi.subfootball.R
import com.dedi.subfootball.adapter.TeamsAdapter
import com.dedi.subfootball.api.ApiService
import com.dedi.subfootball.api.MyApi
import com.dedi.subfootball.model.TeamsItem
import com.dedi.subfootball.repository.FavoriteRepositoryImpl
import com.dedi.subfootball.repository.TeamRepositoryImpl
import com.dedi.subfootball.rx.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_team.*


class TeamFragment : Fragment() , TeamView.View{
    override fun showTeams(teamList: List<TeamsItem>) {
        teamLists.clear()
        teamLists.addAll(teamList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvTeam.layoutManager = layoutManager
        rvTeam.adapter = TeamsAdapter(teamLists, context)
    }

    private var teamLists : MutableList<TeamsItem> = mutableListOf()
    lateinit var mPresenter : TeamPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sercive = ApiService.getClient().create(MyApi::class.java)
        val favoriteRepositoryImpl = FavoriteRepositoryImpl(context!!)
        val teamRepositoryImpl = TeamRepositoryImpl(sercive)
        val scheduler = AppSchedulerProvider()
        mPresenter = TeamPresenter(this, favoriteRepositoryImpl, teamRepositoryImpl, scheduler)
        mPresenter.getTeamData()


    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}

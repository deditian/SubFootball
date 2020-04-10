package com.dedi.subfootball.feature.listplayer


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dedi.subfootball.R
import com.dedi.subfootball.adapter.ListPlayerAdapter
import com.dedi.subfootball.api.ApiService
import com.dedi.subfootball.api.MyApi
import com.dedi.subfootball.model.PlayersItem
import com.dedi.subfootball.model.TeamsItem
import com.dedi.subfootball.repository.PlayersRepositoryImpl
import com.dedi.subfootball.rx.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_list_player.*


class ListPlayerFragment : Fragment(), ListPlayerView.View{
    private var listPlayer : MutableList<PlayersItem> = mutableListOf()
    lateinit var mPresenter: ListPlayerView.Presenter
    override fun showPlayers(playerList: List<PlayersItem>) {
        listPlayer.clear()
        listPlayer.addAll(playerList)
        val layoutManager = GridLayoutManager(context, 3)
        rvListPlayer.layoutManager = layoutManager
        rvListPlayer.adapter = ListPlayerAdapter(listPlayer, context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val team: TeamsItem? = arguments?.getParcelable("teams")
        val service = ApiService.getClient().create(MyApi::class.java)
        val request = PlayersRepositoryImpl(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = ListPlayerPresenter(this, request, scheduler)
        mPresenter.getAllPlayer(team?.idTeam)
    }
}

package com.dedi.subfootball.feature.teamoverview


import android.os.Bundle
import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.dedi.subfootball.R
import com.dedi.subfootball.model.TeamsItem
import kotlinx.android.synthetic.main.fragment_team_overview.*

class TeamOverviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val teamItem: TeamsItem? = arguments?.getParcelable("teams")
        Glide.with(this)
                .load(teamItem?.strTeamBadge)
                .apply(RequestOptions().placeholder(R.drawable.ic_file_download_black_24dp))
                .into(imgBadge)

        teamName.text = teamItem?.strTeam
        tvManager.text = teamItem?.strManager
        tvStadium.text = teamItem?.strStadium
        teamOverview.text = teamItem?.strDescriptionEN
    }


}

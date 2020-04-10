package com.dedi.subfootball.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dedi.subfootball.R
import com.dedi.subfootball.feature.teamsdetail.TeamsDetailActivity
import com.dedi.subfootball.model.TeamsItem
import kotlinx.android.synthetic.main.item_teams.view.*
import org.jetbrains.anko.startActivity


class TeamsAdapter (val teamList: List<TeamsItem>, val context: Context?): RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.item_teams, parent, false))
    }

    override fun getItemCount() = teamList.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teamList[position])
    }


    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(team: TeamsItem){
            itemView.tvTeam.text = team.strTeam
            Glide.with(itemView.context)
                    .load(team.strTeamBadge)
                    .apply(RequestOptions().placeholder(R.drawable.ic_file_download_black_24dp))
                    .into(itemView.imgTeam)

            itemView.setOnClickListener {
                itemView.context.startActivity<TeamsDetailActivity>("team" to team)
            }
        }

    }
}
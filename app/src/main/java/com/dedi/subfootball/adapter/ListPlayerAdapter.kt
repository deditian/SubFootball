package com.dedi.subfootball.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dedi.subfootball.R
import com.dedi.subfootball.feature.playersdetail.PlayersDetailActivity
import com.dedi.subfootball.model.PlayersItem
import kotlinx.android.synthetic.main.item_list_players.view.*
import org.jetbrains.anko.startActivity

class ListPlayerAdapter (val listPlayerItem:List<PlayersItem>, val context: Context?):
        RecyclerView.Adapter<ListPlayerAdapter.ListPlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPlayerViewHolder {
        return ListPlayerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_players, parent, false))
    }

    override fun getItemCount()= listPlayerItem.size

    override fun onBindViewHolder(holder: ListPlayerViewHolder, position: Int) {
        holder.bind(listPlayerItem[position])
    }


    inner class ListPlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(player: PlayersItem){
            itemView.tvPlayer.text = player.strPlayer
            itemView.tvpotision.text = player.strPosition
            Glide.with(itemView.context)
                    .load(player.strCutout)
                    .apply(RequestOptions().placeholder(R.drawable.ic_file_download_black_24dp))
                    .apply(RequestOptions().override(120, 140))
                    .into(itemView.imgDate)

            itemView.setOnClickListener {
                itemView.context.startActivity<PlayersDetailActivity>("player" to player)
            }
        }

    }
}
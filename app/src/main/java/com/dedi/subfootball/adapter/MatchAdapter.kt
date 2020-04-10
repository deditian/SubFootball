package com.dedi.subfootball.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dedi.subfootball.R
import com.dedi.subfootball.feature.detail.Detail
import com.dedi.subfootball.model.ResultsItemMatch
import kotlinx.android.synthetic.main.list_last_item.view.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

import android.app.Activity
import android.content.Intent
import android.provider.CalendarContract


class MatchAdapter (val matchList: List<ResultsItemMatch>,val act: Activity) : RecyclerView.Adapter<MatchAdapter.LastViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastViewHolder {
        return LastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_last_item, parent, false))
    }

    override fun getItemCount(): Int {
        return matchList.size
    }

    override fun onBindViewHolder(holder: LastViewHolder, position: Int) {
        holder.bind(matchList[position])
    }


    inner class LastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(results: ResultsItemMatch) {
            itemView.dateScheduleTv.text = results.dateEvent?.let { formatDate(it) }
            itemView.homeNameTv.text = results.strHomeTeam
            itemView.homeScoreTv.text = results.intHomeScore
            itemView.awayNameTv.text = results.strAwayTeam
            itemView.awayScoreTv.text = results.intAwayScore
            itemView.txt_time.text = results.strTime?.let { formatTime(it) }
            println("timenya " + results.strTime)
            println("homenama : " + results.strHomeTeam)


            itemView.setOnClickListener {
                itemView.context.startActivity<Detail>("match" to results)

            }

            itemView.imgDate.setOnClickListener {
                requestdate()
            }


        }
    }

    private fun requestdate() {
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra(CalendarContract.Events.TITLE, "EVENT_NAME")
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, "BEGIN_TIME")
        intent.putExtra(CalendarContract.CalendarAlerts.ALARM_TIME, "ALARM_TIME")
        act.startActivity(intent)
    }

    fun formatDate(date: Date): String {
        return SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(date)
    }

    fun formatTime(time: String): String? {
        val sdf = SimpleDateFormat("HH:mm")
        val date = sdf.parse(time)
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        System.out.println(sdf.format(date))
        return sdf.format(date)
    }
}



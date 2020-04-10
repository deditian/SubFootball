package com.dedi.subfootball.feature.playersdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dedi.subfootball.R
import com.dedi.subfootball.api.ApiService
import com.dedi.subfootball.api.MyApi
import com.dedi.subfootball.model.PlayersDetailItem
import com.dedi.subfootball.model.PlayersItem
import com.dedi.subfootball.repository.PlayersRepositoryImpl
import com.dedi.subfootball.rx.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_players_detail.*
import kotlinx.android.synthetic.main.player_layout_detail.*

class PlayersDetailActivity : AppCompatActivity() , PlayersDetailView.View{

    lateinit var playerItem: PlayersItem
    lateinit var mPresenter: PlayersDetailView.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players_detail)
        setSupportActionBar(toolbar)
        playerItem = intent.getParcelableExtra("player")
        println("detailnya : "+playerItem)
        supportActionBar?.title = playerItem.strPlayer
        val service = ApiService.getClient().create(MyApi::class.java)
        val request = PlayersRepositoryImpl(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = PlayersDetailPresenter(this, request, scheduler)
        mPresenter.getPlayerData(playerItem.idPlayer!!)
    }
    override fun showPlayerDetail(player: PlayersDetailItem) {
        if(!player.strFanart1.equals(null)){
            Glide.with(applicationContext)
                    .load(player.strFanart1)
                    .apply(RequestOptions().placeholder(R.drawable.ic_file_download_black_24dp))
                    .into(imageBannerPlayer)
        }else{
            Glide.with(applicationContext)
                    .load(player.strThumb)
                    .apply(RequestOptions().placeholder(R.drawable.ic_file_download_black_24dp))
                    .into(imageBannerPlayer)
        }
        Glide.with(applicationContext)
                .load(player.strCutout)
                .apply(RequestOptions().placeholder(R.drawable.ic_file_download_black_24dp))
                .into(imgDate)


        playerName.text = player.strPlayer
        tvPosition.text = player.strPosition
        tvDate.text = player.dateBorn
        playerOverview.text = player.strDescriptionEN
    }
    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}

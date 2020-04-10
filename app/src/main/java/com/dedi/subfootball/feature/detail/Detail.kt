package com.dedi.subfootball.feature.detail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dedi.subfootball.R
import com.dedi.subfootball.R.drawable.ic_add_to_favorites
import com.dedi.subfootball.R.drawable.ic_added_to_favorites
import com.dedi.subfootball.db.FavoriteField
import com.dedi.subfootball.api.ApiService
import com.dedi.subfootball.api.MyApi
import com.dedi.subfootball.model.ResultsItemMatch
import com.dedi.subfootball.model.TeamsItem
import com.dedi.subfootball.repository.FavoriteRepositoryImpl
import com.dedi.subfootball.repository.TeamRepositoryImpl
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class Detail : AppCompatActivity(),DetailView.View {
    override fun setFavoriteState(favList: List<FavoriteField>) {
        if (!favList.isEmpty()) isFavorite = true
    }
    private var isFavorite: Boolean = false
    lateinit var mPresenter : DetailPresenter
    lateinit var resultsItemMatch: ResultsItemMatch
    private var menuItem: Menu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val service = ApiService.getClient().create(MyApi::class.java)
        val request = TeamRepositoryImpl(service)
        val favRepo = FavoriteRepositoryImpl(applicationContext)
        mPresenter = DetailPresenter(this, request,favRepo)

        resultsItemMatch = intent.getParcelableExtra("match")
        mPresenter.getTeamHome(resultsItemMatch.idHomeTeam)
        mPresenter.getTeamAway(resultsItemMatch.idAwayTeam)
        mPresenter.checkMatch(resultsItemMatch.idEvent)
        initData(resultsItemMatch)

    }

    private fun initData(resultsItemMatch: ResultsItemMatch) {
        dateScheduleTv.text = resultsItemMatch.dateEvent?.let { formatDateToMatch(it) }
        gkHomeTv.text = resultsItemMatch.strHomeLineupGoalkeeper
        gkAwayTv.text = resultsItemMatch.strAwayLineupGoalkeeper
        defHomeTv.text = resultsItemMatch.strHomeLineupDefense
        defAwayTv.text = resultsItemMatch.strAwayLineupDefense
        midHomeTv.text = resultsItemMatch.strHomeLineupMidfield
        midAwayTv.text = resultsItemMatch.strAwayLineupMidfield
        forHomeTv.text = resultsItemMatch.strHomeLineupForward
        forAwayTv.text = resultsItemMatch.strAwayLineupForward
        subHomeTv.text = resultsItemMatch.strHomeLineupSubstitutes
        subAwayTv.text = resultsItemMatch.strAwayLineupSubstitutes
        homeNameTv.text = resultsItemMatch.strHomeTeam
        homeScoreTv.text = resultsItemMatch.intHomeScore
        awayNameTv.text = resultsItemMatch.strAwayTeam
        awayScoreTv.text = resultsItemMatch.intAwayScore
        homeScorerTv.text = resultsItemMatch.strHomeGoalDetails
        awayScorerTv.text = resultsItemMatch.strAwayGoalDetails
    }

    override fun showTeamHome(team: TeamsItem) {
        Glide.with(applicationContext)
                .load(team.strTeamBadge)
                .apply(RequestOptions().placeholder(R.drawable.ic_launcher_background))
                .into(homeImg)
    }

    override fun showTeamAway(team: TeamsItem) {
        Glide.with(applicationContext)
                .load(team.strTeamBadge)
                .apply(RequestOptions().placeholder(R.drawable.ic_launcher_background))
                .into(awayImg)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.team_match, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {

            R.id.favorite -> {
                if (isFavorite) {
                    mPresenter.deleteMatch(resultsItemMatch.idEvent)
                    toast("Event Removed to favorite")
                } else {
                    mPresenter.insertMatch(
                            resultsItemMatch.idEvent,
                            resultsItemMatch.idHomeTeam,
                            resultsItemMatch.idAwayTeam)
                    toast("Event Added to favorite")
                }
                isFavorite = !isFavorite
                setFavorite()
                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }


        private fun formatDateToMatch(it: Date): CharSequence? {
            return SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(it)
        }
}

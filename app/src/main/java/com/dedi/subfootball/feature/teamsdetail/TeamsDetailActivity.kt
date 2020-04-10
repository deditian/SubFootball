package com.dedi.subfootball.feature.teamsdetail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dedi.subfootball.R
import com.dedi.subfootball.adapter.ViewPagerAdapter
import com.dedi.subfootball.db.FavoriteTeam
import com.dedi.subfootball.feature.listplayer.ListPlayerFragment
import com.dedi.subfootball.feature.teamoverview.TeamOverviewFragment
import com.dedi.subfootball.model.TeamsItem
import com.dedi.subfootball.repository.FavoriteRepositoryImpl
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.toast

class TeamsDetailActivity : AppCompatActivity(), TeamsDetailView.View {
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null
    lateinit var team: TeamsItem
    lateinit var mPresenter: TeamsDetailView.Presenter

    override fun setFavoriteState(favList: List<FavoriteTeam>) {
        if(!favList.isEmpty()) isFavorite = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        setSupportActionBar(toolbar)

        team = if(savedInstanceState != null){
            savedInstanceState.getParcelable("team")
        }else{
            intent.getParcelableExtra("team")
        }
        val bundle = Bundle()
        bundle.putParcelable("teams", team)
        supportActionBar?.title = team.strTeam
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadImage()
        val favorRepo = FavoriteRepositoryImpl(applicationContext)
        mPresenter = TeamsDetailPresenter(this, favorRepo)
        team.idTeam?.let { (mPresenter as TeamsDetailPresenter).checkTeam(it) }

        val adapter = ViewPagerAdapter(supportFragmentManager)
        val teamoverviewFragment = TeamOverviewFragment()
        val listplayersFragment = ListPlayerFragment()
        teamoverviewFragment.arguments = bundle
        listplayersFragment.arguments = bundle
        adapter.addFragment(teamoverviewFragment, "Team Overview")
        adapter.addFragment(listplayersFragment, "Players")
        viewpagerTeam.adapter = adapter
        tabs.setupWithViewPager(viewpagerTeam)
    }

    private fun loadImage(){
        if (!team.strTeamFanart1.equals(null)){
            Glide.with(applicationContext)
                    .load(team.strTeamFanart1)
                    .apply(RequestOptions().placeholder(R.drawable.
                            ic_file_download_black_24dp))
                    .apply(RequestOptions().override(220, 160))
                    .into(imageTeam)
        }else{
            Glide.with(applicationContext)
                    .load(team.strTeamBadge)
                    .apply(RequestOptions().placeholder(R.drawable.ic_file_download_black_24dp))
                    .apply(RequestOptions().override(120, 140))
                    .into(imageTeam)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.team_match, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.favorite -> {
                if (!isFavorite){
                    team.idTeam?.let { team.strTeamBadge?.let { it1 -> mPresenter.insertTeam(it, it1) } }
                    toast("Team added to favorite")
                    isFavorite = !isFavorite
                }else{
                    team.idTeam?.let { mPresenter.deleteTeam(it) }
                    toast("Team removed from favorite")
                    isFavorite = !isFavorite
                }
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("team", team)

    }
}
package com.dedi.subfootball

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dedi.subfootball.feature.FavFragment

import com.dedi.subfootball.feature.MatchFragment
import com.dedi.subfootball.feature.teams.TeamsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_navigation.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.lastMatch -> {
                    lastMatch(savedInstanceState)
                }

                R.id.lastTeam -> {
                    teamsMatch(savedInstanceState)
                }

                R.id.favMatch -> {
                    favoritesMatch(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.lastMatch
    }

    private fun favoritesMatch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavFragment(), FavFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun teamsMatch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TeamsFragment(), TeamsFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun lastMatch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MatchFragment(), MatchFragment::class.java.simpleName)
                    .commit()
        }
    }



}
package com.dedi.subfootball.feature.favoritematch


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dedi.subfootball.R
import com.dedi.subfootball.adapter.MatchAdapter
import com.dedi.subfootball.api.ApiService
import com.dedi.subfootball.api.MyApi
import com.dedi.subfootball.model.ResultsItemMatch
import com.dedi.subfootball.repository.FavoriteRepositoryImpl
import com.dedi.subfootball.repository.MatchRepositoryImpl
import kotlinx.android.synthetic.main.fragment_favorite.*



class FavoriteFragment : Fragment() , FavoriteView.View{
    private var matchLists : MutableList<ResultsItemMatch> = mutableListOf()
    lateinit var mPresenter : FavoritePresenter

    override fun displayFootballMatch(matchList: List<ResultsItemMatch>) {
        matchLists.clear()
        matchLists.addAll(matchList)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvFavoriteMatch.layoutManager = layoutManager
        rvFavoriteMatch.adapter = activity?.let { MatchAdapter(matchList, it) }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val service = ApiService.getClient().create(MyApi::class.java)
        val request = MatchRepositoryImpl(service)
        val localRepositoryImpl = FavoriteRepositoryImpl(context!!)
        mPresenter = FavoritePresenter(this, request, localRepositoryImpl)
        mPresenter.getFootballMatchData()

    }



    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroyPresenter()
    }

}

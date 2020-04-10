package com.dedi.subfootball.repository

import com.dedi.subfootball.api.MyApi
import com.dedi.subfootball.model.ResponseMatch
import io.reactivex.Flowable

class MatchRepositoryImpl(private val myApi: MyApi){
    fun searchMatches(query: String?) = myApi.searchMatches(query)
    fun getLastMatch(id: String): Flowable<ResponseMatch> = myApi.getLastFootball(id)
    fun getNextMatch(id: String): Flowable<ResponseMatch> = myApi.getNextFootball(id)
    fun getEventById(id: String): Flowable<ResponseMatch> = myApi.getEventById(id)
}
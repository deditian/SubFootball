package com.dedi.subfootball.feature.teamsdetail

import com.dedi.subfootball.repository.FavoriteRepositoryImpl

class TeamsDetailPresenter(val mView: TeamsDetailView.View,
                           val favoriteRepositoryImpl: FavoriteRepositoryImpl): TeamsDetailView.Presenter {
    override fun deleteTeam(id: String) {
        favoriteRepositoryImpl.deleteTeamsData(id)
    }

    override fun checkTeam(id: String) {
        mView.setFavoriteState(favoriteRepositoryImpl.checkFavTeam(id))
    }

    override fun insertTeam(id: String, imgUrl: String) {
        favoriteRepositoryImpl.insertTeamData(id, imgUrl)
    }
}
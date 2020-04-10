package com.dedi.subfootball.feature.next


import com.dedi.subfootball.model.ResponseMatch
import com.dedi.subfootball.model.ResultsItemMatch
import com.dedi.subfootball.repository.MatchRepositoryImpl
import com.dedi.subfootball.rx.SchedulerProvider
import com.dedi.subfootball.rx.TestSchedulerProvider
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class NextPresenterTest {
    @Mock
    lateinit var mView: NextView.View

    @Mock
    lateinit var matchRepositoryImpl: MatchRepositoryImpl



    lateinit var mPresenter: NextPresenter

    lateinit var match : ResponseMatch

    lateinit var responseMatch: Flowable<ResponseMatch>
    lateinit var scheduler: SchedulerProvider
    private val resultsItemMatch = mutableListOf<ResultsItemMatch>()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = TestSchedulerProvider()
        match = ResponseMatch(resultsItemMatch)
        responseMatch = Flowable.just(match)
        mPresenter = NextPresenter(mView, matchRepositoryImpl,scheduler)
        `when`(matchRepositoryImpl.getLastMatch("4328")).thenReturn(responseMatch)
    }

    @Test
    fun getNextMatchData() {
        val id = "4328"
        mPresenter.getNextMatchData(id)
        verify(mView).showNextList(resultsItemMatch)
    }
}